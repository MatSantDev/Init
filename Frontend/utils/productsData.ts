'use server'

import { revalidatePath } from 'next/cache';

import { Product } from '@/types/product';

export async function getProducts() {
  try {
    const res = await fetch(`${ process.env.API_URL }/produtos/consultarTodos`)
    let data: Product[]

    if ( !res.ok ) return data = []

    data = await res.json()

    return data;

  } catch ( err ) {
    console.log( err )
    return []
  }

}

export async function addProduct( formData: FormData ) {
  const newProduct = {
    nome: formData.get('nome'),
    descricao: formData.get('descricao'),
    valorUnitario: Number(formData.get('valorUnitario')),
    estoque: Number(formData.get('estoque')),
  }

  try {
    const res = await fetch(`${ process.env.API_URL }/produtos/inserir`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(newProduct),
    })

    if ( !res.ok ) {
      const errorText = await res.text();
      return { success: false, error: errorText };
    }

    revalidatePath('/products');
    return { success: true };

  } catch ( err ) {
    console.error( err );
    return { success: false, error: 'Falha na comunicação com o servidor Java' };
  }
}

export async function deleteProduct( product: Product ) {
  try {
   const res = await fetch(`${ process.env. API_URL}/produtos/deletar/${ product.id }`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify( product ),
    });

    if ( !res.ok ) {
        throw new Error('Falha ao excluir produto');
    }

    revalidatePath('/products');
    return { success: true };
    
  } catch ( err ) {
    console.error('Erro ao excluir:',  err );
    return { success: false, error: 'Falha ao deletar produto' };
  }
}

export async function updateProduct( product: Product ) {
   try {
   const res = await fetch(`${ process.env. API_URL}/produtos/atualizar`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify( product ),
    });

    if ( !res.ok ) {
        throw new Error('Falha ao editar produto');
    }

    revalidatePath('/products');
    return { success: true };
    
  } catch ( err ) {
    console.error('Erro ao excluir:',  err );
    return { success: false, error: 'Falha ao editar produto' };
  }
}