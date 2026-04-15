'use server'

import { revalidatePath } from 'next/cache';

import { Client } from '@/types/client';
import { getBudgets } from '@/utils/budgetsData';

export async function getClients() {
  try {
    const res = await fetch(`${ process.env.API_URL }/clientes/consultarTodos`)
    let data: Client[]

    if ( !res.ok ) return data = []

    data = await res.json()

    return data;

  } catch ( err ) {
    console.log( err )
    return []
  }
}

export async function addClient( formData: FormData ) {
    const newClient = {
      id: 0, //! ID do novo cliente não fica zerado, necessário aplicar para funcionar
      nome: formData.get('nome'),
      email: formData.get('email'),
      telefone: formData.get('telefone'),
      cpf: formData.get('cpf'),
      cep: formData.get('cep'),
      endereco: formData.get('endereco'),
      sexo: formData.get('sexo'),
      dataNascimento: formData.get('dataNascimento'),
      criadoEm: new Date(),
    }

    try {
      const res = await fetch(`${ process.env.API_URL }/clientes/inserir`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify( newClient ),
      })
  
      if ( !res.ok ) {
        const errorText = await res.text();
        return { success: false, error: errorText };
      }
  
      revalidatePath('/clients');
      return { success: true };
  
    } catch ( err ) {
      console.error( err );
      return { success: false, error: 'Falha na comunicação com o servidor Java' };
    }
}

export async function deleteClient( client: Client ) {
  try {
   const res = await fetch(`${ process.env. API_URL}/clientes/deletar/${ client.id }`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify( client ),
    });

    if ( !res.ok ) {
        throw new Error('Falha ao excluir cliente');
    }

    revalidatePath('/clients');
    return { success: true };
    
  } catch ( err ) {
    console.error('Erro ao excluir:', err );
    return { success: false, error: 'Falha ao deletar cliente' };
  }
  return { success: true }
}

export async function updateClient( client: Client ) {
   try {
   const res = await fetch(`${ process.env. API_URL}/clientes/atualizar`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify( client ),
    });

    if ( !res.ok ) {
        throw new Error('Falha ao editar cliente');
    }

    revalidatePath('/clients');
    return { success: true };
    
  } catch ( err ) {
    console.error('Erro ao excluir:', err );
    return { success: false, error: 'Falha ao editar cliente' };
  }
}


export async function getClientsWithBudgets() {
  const clients = await getClients()
  const budgets = await getBudgets()

  const countMap: Record<number, number> = {}

  for ( const budget of budgets ) {
    const clientId = budget.cliente.id
    countMap[ clientId ] = ( countMap[ clientId ] || 0 ) + 1
  }

  const clientsWithTotal = clients.map(client => ({
    ...client,
    totalBudgets: countMap[client.id] || 0
  }))

  return clientsWithTotal
}