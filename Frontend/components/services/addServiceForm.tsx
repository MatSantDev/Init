'use client'

import { useState, useRef } from 'react'
import { toast } from 'sonner'

import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { DialogClose } from '@/components/ui/dialog'

import { addService } from '@/utils/servicesData'

export function AddServiceForm() {
  const [ isLoading, setIsLoading ] = useState(false)
  const closeRef = useRef<HTMLButtonElement>(null)

  async function handleSubmit( e: React.FormEvent<HTMLFormElement> ) {
    e.preventDefault()
    setIsLoading(true)

    const formData = new FormData( e.currentTarget )

    try {
      const result = await addService( formData )

      if ( result.success ) {
        if ( closeRef.current ) {
          closeRef.current.click()
        }
        toast.success( 'Serviço adicionado com sucesso!' )
      } else {
        toast.error('Falha ao adicionar o serviço. Tente novamente mais tarde')
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
          Nome do Serviço
        </label>
        <Input id='nome' name='nome' placeholder='Ex: Limpeza de computador' required />
      </div>

      <div className='flex flex-col gap-2'>
        <label htmlFor='descricao' className='text-sm font-semibold'>
          Descrição
        </label>
        <Input id='descricao' name='descricao' placeholder='Ex: Teclado switch azul...' required />
      </div>
      
      <div className='flex gap-4'>
        <div className='flex flex-col gap-2 w-full'>
          <label htmlFor='valorUnitario' className='text-sm font-semibold'>
            Valor Unitário (R$)
          </label>
          <Input id='valorUnitario' name='valorUnitario' type='number' step='0.01' min='0' placeholder='Ex: 150.00' required />
        </div>
      </div>

      <Button
        type='submit'
        className='mt-4 w-full'
        disabled={ isLoading }
      >
        { isLoading ? 'Salvando...' : 'Salvar Serviço' }
      </Button>

      <DialogClose ref={ closeRef } className='hidden' />
    </form>
  )
}