'use client'

import { useState, useRef } from 'react'
import { toast } from 'sonner'

import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { DialogClose } from '@/components/ui/dialog'

import { addProduct } from '@/utils/productsData'

export function AddProductsForm() {
  const [ isLoading, setIsLoading ] = useState(false)
  const closeRef = useRef<HTMLButtonElement>(null)

  async function handleSubmit( e: React.FormEvent<HTMLFormElement> ) {
    e.preventDefault()
    setIsLoading(true)

    const formData = new FormData(e.currentTarget)

    try {
      const result = await addProduct( formData )

      if ( result.success ) {
        if ( closeRef.current ) {
          closeRef.current.click()
        }
        toast.success( 'Produto adicionado com sucesso!' )
      } else {
        toast.error('Falha ao adicionar o produto. Tente novamente mais tarde')
        console.error('Erro do servidor:', result.error )
      }

    } catch ( err ) {
      console.error('Erro ao conectar com a API:', err )
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <form onSubmit={ handleSubmit } className='flex flex-col gap-4 py-4'>
      <div className='flex flex-col gap-2'>
        <label htmlFor='nome' className='text-sm font-semibold'>
          Nome do Produto
        </label>
        <Input id='nome' name='nome' placeholder='Ex: Teclado Mecânico' required />
      </div>

      <div className='flex flex-col gap-2'>
        <label htmlFor='descricao' className='text-sm font-semibold'>
          Descrição
        </label>
        <Input id='descricao' name='descricao' placeholder='Ex: Teclado switch azul...' required />
      </div>
      
      <div className='flex gap-4'>
        <div className='flex flex-col gap-2 w-1/2'>
          <label htmlFor='valorUnitario' className='text-sm font-semibold'>
            Valor Unitário (R$)
          </label>
          <Input id='valorUnitario' name='valorUnitario' type='number' step='0.01' placeholder='Ex: 150.00' required />
        </div>

        <div className='flex flex-col gap-2 w-1/2'>
          <label htmlFor='estoque' className='text-sm font-semibold'>
            Estoque Inicial
          </label>
          <Input id='estoque' name='estoque' type='number' placeholder='Ex: 10' required />
        </div>
      </div>

      <Button
        type='submit'
        className='mt-4 w-full'
        disabled={ isLoading }
      >
        { isLoading ? 'Salvando...' : 'Salvar Produto' }
      </Button>

      <DialogClose ref={ closeRef } className='hidden' />
    </form>
  )
}