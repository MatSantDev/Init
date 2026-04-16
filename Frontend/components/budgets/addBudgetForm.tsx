'use client'

import { useState, useRef } from 'react'
import { toast } from 'sonner'

import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { DialogClose } from '@/components/ui/dialog'
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"

import { addBudget } from '@/utils/budgetsData'
import { Client } from '@/types/client'

interface AddBudgetFormProps {
  clients: Client[]
}

export function AddBudgetForm( { clients }: AddBudgetFormProps ) {
  const [ isLoading, setIsLoading ] = useState(false)
  const [ selectedClientId, setSelectedClientId ] = useState<string>('')
  const closeRef = useRef<HTMLButtonElement>(null)

  async function handleSubmit( e: React.FormEvent<HTMLFormElement> ) {
    e.preventDefault()
    setIsLoading(true)

    const formData = new FormData( e.currentTarget )

    if ( !selectedClientId ) {
      toast.error('Por favor, selecione um cliente.')
      return
    }
    formData.append('cliente_id', selectedClientId )
    
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
          Selecione o Cliente:
        </label>
        <Select onValueChange={ setSelectedClientId } value={ selectedClientId }>
          <SelectTrigger className="w-full">
            <SelectValue placeholder="Selecione um cliente" />
          </SelectTrigger>
          <SelectContent>
            <SelectGroup>
              { clients?.map( client => (
                <SelectItem key={ client.id } value={ client.id.toString() } >
                  { client.nome }
                </SelectItem>
              ) ) }
            </SelectGroup>
          </SelectContent>
        </Select>
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
        <Input
          id='observacao'
          name='observacao'
          placeholder='Ex: Orçamento para alvenaria'
          required
        />
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