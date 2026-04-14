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
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"

import { Budget } from '@/types/budget'
import { Client } from '@/types/client'

import { updateBudget } from '@/utils/budgetsData'
import { formatDateForInput } from '@/utils/formatDateForInput'

type EditBudgetModalProps = {
  open: boolean
  onOpenChange: ( open: boolean ) => void
  budget: Budget
  clients: Client[]
  onSuccess: () => void
}

export function EditBudgetModal({ open, onOpenChange, budget, clients, onSuccess }: EditBudgetModalProps) {
  const initialClientId = typeof budget.cliente === 'object'
    ? ( budget.cliente as any ).id?.toString()
    : String( budget.cliente );

  const [ selectedClientId, setSelectedClientId ] = useState<string>( initialClientId )
  const [ dataOrcamento, setDataOrcamento ] = useState( formatDateForInput(budget.dataOrcamento) )
  const [ observacao, setObservacao ] = useState( budget.observacao )
  const [ valorTotal, setValorTotal ] = useState( budget.valorTotal )

  const [ isLoading, setIsLoading ] = useState( false )

  async function handleSubmit( e: React.FormEvent ) {
    e.preventDefault()

    const hasChanges =
      selectedClientId !== initialClientId ||
      dataOrcamento !== formatDateForInput( budget.dataOrcamento ) ||
      observacao !== budget.observacao ||
      Number( valorTotal ) !== budget.valorTotal;

    if ( !hasChanges ) {
      toast.info('Nenhuma alteração foi detectada para atualizar.')
      return
    }

    try {
      setIsLoading( true )

      const updatedBudget = {
        ...budget,
        cliente: { id: Number(selectedClientId) },
        dataOrcamento: new Date(dataOrcamento),
        observacao,
        valorTotal: Number(valorTotal),
      }

      const result = await updateBudget( updatedBudget as unknown as Budget )

      if ( !result.success ) {
        toast.error( result.error )
        setIsLoading( false )
        return
      }

      toast.success('Orçamento atualizado com sucesso!')
      onSuccess()
      onOpenChange(false)

    } catch ( err ) {
      console.error( 'Erro ao atualizar:', err )
      toast.error( 'Erro ao atualizar o orçamento.' )
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <Dialog open={ open } onOpenChange={ onOpenChange }>
      <DialogContent className='sm:max-w-106.25'>
        <DialogHeader>
          <DialogTitle>Editar Orçamento</DialogTitle>
        </DialogHeader>

        <form onSubmit={ handleSubmit } className='flex flex-col gap-4 py-4'>
          
          <div className='flex flex-col gap-2'>
            <label htmlFor='cliente' className='text-sm font-medium'> Cliente </label>
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
            <label htmlFor='dataOrcamento' className='text-sm font-medium'> Data do Orçamento </label>
            <input
              id='dataOrcamento'
              type='date'
              value={ dataOrcamento }
              onChange={ ( e ) => setDataOrcamento( e.target.value ) }
              className='flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm'
              required
            />
          </div>

          <div className='flex flex-col gap-2'>
            <label htmlFor='observacao' className='text-sm font-medium'> Observação </label>
            <input
              id='observacao'
              value={ observacao }
              onChange={ ( e ) => setObservacao( e.target.value ) }
              className='flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm'
              required
            />
          </div>

          <div className='flex flex-col gap-2'>
            <label htmlFor='valorTotal' className='text-sm font-medium'> Valor Total (R$) </label>
            <input
              id='valorTotal'
              type='number'
              step="0.01"
              value={ valorTotal }
              onChange={ ( e ) => setValorTotal( Number( e.target.value ) ) }
              className='flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm'
              required
            />
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