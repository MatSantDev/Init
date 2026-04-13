'use client'

import { useState, useRef } from 'react'
import { toast } from 'sonner'

import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { DialogClose } from '@/components/ui/dialog'

import { addBudget } from '@/utils/budgetsData'

export function AddBudgetForm() {
  const [ isLoading, setIsLoading ] = useState(false)
  const closeRef = useRef<HTMLButtonElement>(null)

  async function handleSubmit( e: React.FormEvent<HTMLFormElement> ) {
    e.preventDefault()
    setIsLoading(true)

    const formData = new FormData( e.currentTarget )

    const today = new Date()
    today.setHours( 0, 0, 0, 0 )

    const dataOrcamento = formData.get('dataOrcamento') as string
    const selectedDate = new Date( dataOrcamento )
    selectedDate.setHours( 0, 0, 0, 0 )

    if ( selectedDate < today ) {
      setIsLoading( false )
      toast.error('Não é permitido criar orçamentos para datas que ja passaram.')
      return
    }

    try {
      const result = await addBudget( formData )

      if ( result.success ) {
        if ( closeRef.current ) {
          closeRef.current.click()
        }
        toast.success( 'Orçamento adicionado com sucesso!' )
      } else {
        toast.error('Falha ao adicionar o orçamento. Tente novamente mais tarde')
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
        <label htmlFor='cliente' className='text-sm font-semibold'>
          Nome do cliente
        </label>
        <Input id='cliente' name='cliente' placeholder='Ex: Jonas Silva' required />
      </div>

      <div className='flex flex-col gap-2'>
        <label htmlFor='dataOrcamento' className='text-sm font-semibold'>
          Data do orçamento
        </label>
        <Input
          id='dataOrcamento'
          name='dataOrcamento'
          type='date'
          placeholder='Ex: Teclado switch azul...'
          required
        />
      </div>
      
      <div className='flex flex-col gap-2'>
        <label htmlFor='nome' className='text-sm font-semibold'>
          Observação
        </label>
        <Input id='observacao' name='observacao' placeholder='Ex: Orçamento para alvenaria' required />
      </div>

      <div className='flex flex-col gap-2 w-full'>
        <label htmlFor='valorTotal' className='text-sm font-semibold'>
          Valor Total (R$)
        </label>
        <Input id='valorTotal' name='valorTotal' type='number' step='0.01' placeholder='Ex: 150.00' required />
      </div>

      <Button
        type='submit'
        className='mt-4 w-full'
        disabled={ isLoading }
      >
        { isLoading ? 'Salvando...' : 'Salvar Orçamento' }
      </Button>

      <DialogClose ref={ closeRef } className='hidden' />
    </form>
  )
}