'use server'

import { revalidatePath } from 'next/cache';

import { BudgetItem } from '@/types/budgetItem';

export async function getBudgetItems( budgetId: number ) {
  try {
      const res = await fetch(`${ process.env.API_URL }/orcamentos/${ budgetId }/itens/consultarTodosByOrcamentoId`, {
        cache: 'no-store'
      })

      let data: BudgetItem[]
      if ( !res.ok ) return data = []

      data = await res.json()

      return data;
  
    } catch ( err ) {
      console.log( err )
      return []
    }
}

export async function addBudgetItem( formData: FormData, budgetId: number ) {
  let newItem

  const tipo = formData.get('tipoOrcamentoItem')
  const subtotal = Number( formData.get('subtotal') )
  const quantidade = Number( formData.get('quantidade') )
  const valorUnitario = Number( formData.get('valorUnitario') )
  const descricao = formData.get('descricao')

  if ( tipo === 'PRODUTO' ) {
      newItem = {
        orcamento: { id: budgetId },
        tipoOrcamentoItem: tipo,
        quantidade: quantidade,
        valorUnitario: valorUnitario,
        subtotal: subtotal,
        descricao: descricao,
        produto: { id: Number( formData.get('produto_id') ) },
        servico: null,
      }
  } else if ( tipo === 'SERVICO' ) {
      newItem = {
        orcamento: { id: budgetId },
        tipoOrcamentoItem: tipo,
        quantidade: quantidade,
        valorUnitario: valorUnitario,
        subtotal: subtotal,
        descricao: descricao,
        produto: null,
        servico: { id: Number( formData.get('servico_id') ) },
      }
  }

  try {
    const res = await fetch(`${ process.env.API_URL }/orcamentos/${ budgetId }/itens/inserir`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify( newItem ),
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

export async function deleteBudgetItem( budgetId: number, itemId: number ) {
  try {
    const res = await fetch(`${ process.env.API_URL }/orcamentos/${ budgetId }/itens/deletar/${ itemId }`, {
      method: 'DELETE',
    });
    if ( !res.ok ) {
      const errorText = await res.text();
      return { success: false, error: errorText };
    }
    revalidatePath('/budgets');
    return { success: true };
  } catch (err) {
    console.error(err);
    return { success: false, error: 'Falha na comunicação com o servidor' };
  }
}