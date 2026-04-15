'use client'

import { useState } from 'react'
import { toast } from 'sonner'

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from '@/components/ui/dialog'
import { Button } from '@/components/ui/button'

import { Product } from '@/types/product'
import { updateProduct } from '@/utils/productsData' 

type EditProductModalProps = {
  open: boolean
  onOpenChange: ( open: boolean ) => void
  product: Product
  onSuccess: () => void
}

export function EditProductModal({ open, onOpenChange, product, onSuccess }: EditProductModalProps) {
  const [ nome, setNome ] = useState( product.nome )
  const [ descricao, setDescricao ] = useState( product.descricao )
  const [ valorUnitario, setValorUnitario ] = useState( product.valorUnitario )
  const [ estoque, setEstoque ] = useState( product.estoque )
  
  const [ isLoading, setIsLoading ] = useState( false )

  async function handleSubmit( e: React.FormEvent ) {
    e.preventDefault()

    const hasChanges =
      nome !== product.nome ||
      descricao !== product.descricao ||
      Number( valorUnitario ) !== product.valorUnitario ||
      Number( estoque ) !== product.estoque

    if ( !hasChanges ) {
      toast.info('Nenhuma alteração foi detectada para atualizar.')
      return
    }

    try {
      setIsLoading( true )

      const updatedProduct: Product = {
        ...product,
        nome,
        descricao,
        valorUnitario: Number(valorUnitario),
        estoque: Number(estoque)
      }

      const result = await updateProduct( updatedProduct )
      if ( !result.success ) {
        toast.error( result.error )
        setIsLoading( false )
        return
      }

      toast.success('Produto atualizado com sucesso!')
      onSuccess()
      onOpenChange(false)

    } catch ( err ) {
      console.error( 'Erro ao atualizar:', err )
      toast.error( 'Erro ao atualizar o produto.' )
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <Dialog open={ open } onOpenChange={ onOpenChange }>
      <DialogContent className='sm:max-w-106.25'>
        <DialogHeader>
          <DialogTitle>Editar Produto</DialogTitle>
        </DialogHeader>

        <form onSubmit={ handleSubmit } className='flex flex-col gap-4 py-4'>
          <div className='flex flex-col gap-2'>
            <label htmlFor='nome' className='text-sm font-medium'>Nome</label>
            <input
              id='nome'
              value={ nome }
              onChange={ ( e ) => setNome( e.target.value ) }
              className='flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm'
              required
            />
          </div>

          <div className='flex flex-col gap-2'>
            <label htmlFor='descricao' className='text-sm font-medium'>Descrição</label>
            <input
              id='descricao'
              value={ descricao }
              onChange={ ( e ) => setDescricao( e.target.value ) }
              className='flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm'
              required
            />
          </div>

          <div className='grid grid-cols-2 gap-4'>
            <div className='flex flex-col gap-2'>
              <label htmlFor='valorUnitario' className='text-sm font-medium'>Preço</label>
              <input
                id='valorUnitario'
                type='number'
                step='0.01'
                value={ valorUnitario }
                onChange={ ( e ) => setValorUnitario( Number( e.target.value ) ) }
                className='flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm'
                required
              />
            </div>

            <div className='flex flex-col gap-2'>
              <label htmlFor='estoque' className='text-sm font-medium'>Estoque</label>
              <input
                id='estoque'
                type='number'
                value={ estoque }
                onChange={ ( e ) => setEstoque( Number( e.target.value ) ) }
                className='flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm'
                required
              />
            </div>
          </div>

          <DialogFooter className='mt-4'>
            <Button
              type='button'
              variant='outline'
              onClick={ () => onOpenChange( false ) }
              disabled={ isLoading }
            >
              Cancelar
            </Button>
            <Button type='submit' disabled={ isLoading } >
              { isLoading ? 'Salvando...' : 'Salvar Alterações' }
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  )
}