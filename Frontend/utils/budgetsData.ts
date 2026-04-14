'use server'

import { revalidatePath } from 'next/cache';

import { Budget } from '@/types/budget';

export async function getBudgets() {
  try {
    const res = await fetch(`${ process.env.API_URL }/orcamentos/consultarTodos`)
    let data: Budget[]

    console.log( res )

    if ( !res.ok ) return data = []

    data = await res.json()

    return data;

  } catch ( err ) {
    console.log( err )
    return []
  }

}

export async function addBudget( formData: FormData ) {
  const newBudget = {
    cliente: {
      id: Number( formData.get('cliente_id') )
    },
    dataOrcamento: formData.get('dataOrcamento'),
    observacao: formData.get('observacao'),
    valorTotal: Number(formData.get('valorTotal')),
  }
  
  console.log( 'NEW BUDGET' )
  console.log( newBudget )

    try {
      const res = await fetch(`${ process.env.API_URL }/orcamentos/inserir`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify( newBudget ),
      })
  
      if ( !res.ok ) {
        const errorText = await res.text();
        return { success: false, error: errorText };
      }
  
      revalidatePath('/budgets');
      return { success: true };
  
    } catch ( err ) {
      console.error( err );
      return { success: false, error: 'Falha na comunicação com o servidor Java' };
    }
}

export async function deleteBudget( budget: Budget ) {
  try {
   const res = await fetch(`${ process.env. API_URL}/orcamentos/deletar/${ budget.id }`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify( budget ),
    });

    if ( !res.ok ) {
        throw new Error('Falha ao excluir orçamento');
    }

    revalidatePath('/budgets');
    return { success: true };
    
  } catch ( err ) {
    console.error('Erro ao excluir:', err );
    return { success: false, error: 'Falha ao deletar orçamento' };
  }
}

export async function updateBudget( budget: Budget ) {
   try {
   const res = await fetch(`${ process.env. API_URL}/orcamentos/atualizar`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify( budget ),
    });

    if ( !res.ok ) {
        throw new Error('Falha ao editar orçamento');
    }

    revalidatePath('/budgets');
    return { success: true };
    
  } catch ( err ) {
    console.error('Erro ao excluir:', err );
    return { success: false, error: 'Falha ao editar orçamento' };
  }
}