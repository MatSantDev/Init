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

import { Client } from '@/types/client'
import { updateClient } from '@/utils/clientsData'
import { formatDateForInput } from '@/utils/formatDateForInput'

type EditClientModalProps = {
  open: boolean
  onOpenChange: ( open: boolean ) => void
  client: Client
  onSuccess: () => void
}

export function EditClientModal({ open, onOpenChange, client, onSuccess }: EditClientModalProps ) {
  const [ nome, setNome ] = useState( client.nome )
  const [ email, setEmail ] = useState( client.email )
  const [ telefone, setTelefone ] = useState( client.telefone )
  const [ cpf, setCpf ] = useState( client.cpf )
  const [ cep, setCep ] = useState( client.cep )
  const [ endereco, setEndereco ] = useState( client.endereco )
  const [ sexo, setSexo ] = useState( client.sexo )
  const [ dataNascimento, setDataNascimento ] = useState( client.dataNascimento )

  const [ isLoading, setIsLoading ] = useState( false )

  async function handleSubmit( e: React.FormEvent ) {
    // e.preventDefault()

    // const hasChanges =
    //   nome !== client.nome ||
    //   email !== client.email ||
    //   Number( telefone ) !== client.telefone;
    //   cpf !== client.cpf ||
    //   cep !== client.cep ||
    //   endereco !== client.endereco ||
    //   sexo !== client.sexo ||
    //   dataNascimento !== client.dataNascimento

    // if ( !hasChanges ) {
    //   toast.info('Nenhuma alteração foi detectada para atualizar.')
    //   return
    // }

    // try {
    //   setIsLoading( true )

    //   const updatedClient: Client = {
    //     ...client,
    //     nome,
    //     email,
    //     telefone: Number( telefone ),
    //     cpf,
    //     cep,
    //     endereco,
    //     sexo,
    //     dataNascimento: new Date(dataNascimento),
    //   }

    //   const result = await updateClient( updatedClient )
    //   if ( !result.success ) {
    //     toast.error( result.error )
    //     setIsLoading( false )
    //     return
    //   }

    //   toast.success('Cliente atualizado com sucesso!')
    //   onSuccess()
    //   onOpenChange(false)

    // } catch ( err ) {
    //   console.error( 'Erro ao atualizar:', err )
    //   toast.error( 'Erro ao atualizar o cliente.' )
    // } finally {
    //   setIsLoading(false)
    // }
  }

  return (
    <Dialog open={ open } onOpenChange={ onOpenChange }>
      <DialogContent className='sm:max-w-106.25'>
        <DialogHeader>
          <DialogTitle>Editar Orçamento</DialogTitle>
        </DialogHeader>

        <form onSubmit={ handleSubmit } className='flex flex-col gap-4 py-4'>
          <div className='flex flex-col gap-2'>
            <label htmlFor='nome' className='text-sm font-medium'> Nome </label>
            <input
              id='nome'
              value={ nome }
              onChange={ ( e ) => setNome( e.target.value ) }
              className='flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm'
              required
            />
          </div>
{/* 
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
              step='0.01'
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
          </DialogFooter> */}
        </form>
      </DialogContent>
    </Dialog>
  )
}