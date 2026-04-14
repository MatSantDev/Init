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
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'

import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'

import { Client } from '@/types/client'
import { updateClient } from '@/utils/clientsData'
import { formatCEP, formatCPF, formatDateForInput, formatPhone } from '@/utils/formatters'

type EditClientModalProps = {
  open: boolean
  onOpenChange: ( open: boolean ) => void
  client: Client
  onSuccess: () => void
}

export function EditClientModal({ open, onOpenChange, client, onSuccess }: EditClientModalProps ) {
  const [ nome, setNome ] = useState( client.nome )
  const [ email, setEmail ] = useState( client.email )
  const [ phone, setPhone ] = useState( client.telefone )
  const [ cpf, setCpf ] = useState( client.cpf )
  const [ cep, setCep ] = useState( client.cep )
  const [ address, setAddress ] = useState( client.endereco )
  const [ sexo, setSexo ] = useState( client.sexo )
  const [ bornDate, setBornDate ] = useState( formatDateForInput(client.dataNascimento) )

  const [ isLoading, setIsLoading ] = useState( false )

  async function handleSubmit( e: React.FormEvent ) {
    e.preventDefault()

    const hasChanges =
      nome !== client.nome ||
      email !== client.email ||
      phone !== client.telefone ||
      cpf !== client.cpf ||
      cep !== client.cep ||
      address !== client.endereco ||
      sexo !== client.sexo ||
      bornDate !== formatDateForInput(client.dataNascimento)

    if ( !hasChanges ) {
      toast.info('Nenhuma alteração foi detectada para atualizar.')
      return
    }

    try {
      setIsLoading( true )

      const updatedClient: Client = {
        ...client,
        nome,
        email,
        telefone: phone,
        cpf,
        cep,
        endereco: address,
        sexo,
        dataNascimento: new Date( bornDate ),
      }

      const result = await updateClient( updatedClient )
      if ( !result.success ) {
        toast.error( result.error )
        setIsLoading( false )
        return
      }

      toast.success('Cliente atualizado com sucesso!')
      onSuccess()
      onOpenChange(false)

    } catch ( err ) {
      console.error( 'Erro ao atualizar:', err )
      toast.error( 'Erro ao atualizar o cliente.' )
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <Dialog open={ open } onOpenChange={ onOpenChange }>
      <DialogContent className='sm:max-w-106.25'>
        <DialogHeader>
          <DialogTitle>Editar Cliente</DialogTitle>
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

          <div className='flex flex-col gap-2'>
            <label htmlFor='email' className='text-sm font-semibold'>
              Email do cliente
            </label>
            <Input
              id='email'
              name='email'
              value={ email }
              onChange={ ( e ) => setEmail( e.target.value ) }
              required
            />
          </div>

          <div className='flex flex-col gap-2 w-full'>
            <label htmlFor='telefone' className='text-sm font-semibold'>
              Telefone
            </label>
            <Input
              id='telefone'
              name='telefone'
              type='text'
              value={ phone }
              onChange={ ( e ) => setPhone(formatPhone( e.target.value ) ) }
              required
            />
          </div>

          <div className='flex gap-2' >
            <div className='flex flex-col gap-2'>
              <label htmlFor='nome' className='text-sm font-semibold'>
                CPF
              </label>
              <Input
                id='cpf'
                name='cpf'
                value={ cpf }
                onChange={ ( e ) => setCpf(formatCPF( e.target.value ) ) }
                required
              />
            </div>
      
            <div className='flex flex-col gap-2'>
              <label htmlFor='nome' className='text-sm font-semibold'>
                CEP
              </label>
              <Input
                id='cep'
                name='cep'
                value={ cep }
                onChange={ ( e ) => setCep(formatCEP( e.target.value ) ) }
                required
              />
            </div>
          </div>

          <div className='flex gap-2' >

            <div className='flex flex-col gap-2'>
              <label htmlFor='endereco' className='text-sm font-semibold'>
                Endereço
              </label>
              <Input
                id='endereco'
                name='endereco'
                value={ address }
                onChange={ ( e ) => setAddress( e.target.value ) }
                required
              />
            </div>

            <div className='flex flex-col gap-2'>
              <label htmlFor='sexo' className='text-sm font-semibold'>
                Sexo
              </label>
              <Select
                onValueChange={ ( value ) => setSexo( value as "M" | "F" )}
                value={ sexo }
              >
                <SelectTrigger className="w-full h-10">
                  <SelectValue placeholder="Selecione uma opção..." />
                </SelectTrigger>
                <SelectContent>
                  <SelectGroup>
                    <SelectItem value="M">
                      Masculino
                    </SelectItem>
                    <SelectItem value="F">
                      Feminino
                    </SelectItem>
                  </SelectGroup>
                </SelectContent>
              </Select>
            </div>

          </div>

      <div className='flex flex-col gap-2'>
        <label htmlFor='dataNascimento' className='text-sm font-semibold'>
          Data de Nascimento
        </label>
        <Input
          id='dataNascimento'
          name='dataNascimento'
          type='date'
          value={ bornDate }
          onChange={ ( e ) => setBornDate( e.target.value ) }
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