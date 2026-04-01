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

import { Service } from '@/types/service'
import { updateService } from '@/utils/servicesData'

type EditServiceModalProps = {
  open: boolean
  onOpenChange: ( open: boolean ) => void
  service: Service
  onSuccess: () => void
}

export function EditServiceModal({ open, onOpenChange, service, onSuccess }: EditServiceModalProps) {
  const [ nome, setNome ] = useState( service.nome )
  const [ descricao, setDescricao ] = useState( service.descricao )
  const [ valorUnitario, setValorUnitario ] = useState( service.valorUnitario )
  
  const [ isLoading, setIsLoading ] = useState( false )

  async function handleSubmit( e: React.FormEvent ) {
    e.preventDefault()

    const hasChanges =
      nome !== service.nome ||
      descricao !== service.descricao ||
      Number( valorUnitario ) !== service.valorUnitario;

    if ( !hasChanges ) {
      toast.info('Nenhuma alteração foi detectada para atualizar.')
      return
    }

    try {
      setIsLoading( true )

      const updatedService: Service = {
        ...service,
        nome,
        descricao,
        valorUnitario: Number(valorUnitario),
      }

      const result = await updateService( updatedService )
      if ( !result.success ) {
        toast.error( result.error )
        setIsLoading( false )
        return
      }

      toast.success('Serviço atualizado com sucesso!')
      onSuccess()
      onOpenChange(false)

    } catch ( err ) {
      console.error( 'Erro ao atualizar:', err )
      toast.error( 'Erro ao atualizar o serviço.' )
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <Dialog open={ open } onOpenChange={ onOpenChange }>
      <DialogContent className='sm:max-w-106.25'>
        <DialogHeader>
          <DialogTitle>Editar Serviço</DialogTitle>
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

          <div className='flex flex-col gap-2'>
            <label htmlFor='valorUnitario' className='text-sm font-medium'>Valor unitário (R$)</label>
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