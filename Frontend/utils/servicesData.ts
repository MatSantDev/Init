'use server'

import { revalidatePath } from 'next/cache';

import { Service } from '@/types/service';

export async function getServices() {
  try {
    const res = await fetch(`${ process.env.API_URL }/servicos/consultarTodos`)
    let data: Service[]

    if ( !res.ok ) return data = []

    data = await res.json()

    return data;

  } catch ( err ) {
    console.log( err )
    return []
  }

}

export async function addService( formData: FormData ) {
  const newService = {
    nome: formData.get('nome'),
    descricao: formData.get('descricao'),
    valorUnitario: Number(formData.get('valorUnitario')),
  }
  
    try {
      const res = await fetch(`${ process.env.API_URL }/servicos/inserir`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify( newService ),
      })
  
      if ( !res.ok ) {
        const errorText = await res.text();
        return { success: false, error: errorText };
      }
  
      revalidatePath('/services');
      return { success: true };
  
    } catch ( err ) {
      console.error( err );
      return { success: false, error: 'Falha na comunicação com o servidor Java' };
    }
}

export async function deleteService( service: Service ) {
  try {
   const res = await fetch(`${ process.env. API_URL}/servicos/deletar/${ service.id }`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify( service ),
    });

    if ( !res.ok ) {
        throw new Error('Falha ao excluir serviço');
    }

    revalidatePath('/services');
    return { success: true };
    
  } catch ( err ) {
    console.error('Erro ao excluir:',  err );
    return { success: false, error: 'Falha ao deletar serviço' };
  }
}

export async function updateService( service: Service ) {
   try {
   const res = await fetch(`${ process.env. API_URL}/servicos/atualizar`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify( service ),
    });

    if ( !res.ok ) {
        throw new Error('Falha ao editar serviço');
    }

    revalidatePath('/services');
    return { success: true };
    
  } catch ( err ) {
    console.error('Erro ao excluir:',  err );
    return { success: false, error: 'Falha ao editar serviço' };
  }
}