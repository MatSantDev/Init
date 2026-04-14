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

import { addClient } from '@/utils/clientsData'
import { formatPhone, formatCEP, formatCPF } from '@/utils/formatters'

export function AddClientForm() {
  const [ isLoading, setIsLoading ] = useState(false)

  const [ sexo, setSexo ] = useState<'M' | 'F'>('M')
  const [ phone, setPhone ] = useState('')
  const [ cpf, setCpf ] = useState('')
  const [ cep, setCep ] = useState('')

  const closeRef = useRef<HTMLButtonElement>(null)

  async function handleSubmit( e: React.FormEvent<HTMLFormElement> ) {
    e.preventDefault()
    setIsLoading(true)

    const formData = new FormData( e.currentTarget )

    formData.append( 'sexo', sexo )

    const dataNascimento = formData.get('dataNascimento') as string
    const selectedDate = new Date( dataNascimento )
    selectedDate.setHours( 0, 0, 0, 0 )

    const today = new Date()
    today.setHours(0, 0, 0, 0)

    if ( selectedDate > today ) {
      toast.error('A data de nascimento não pode ser no futuro.')
      setIsLoading(false)
      return
    }

    try {
      const result = await addClient( formData )

      if ( result.success ) {
        if ( closeRef.current ) {
          closeRef.current.click()
        }
        toast.success( 'Cliente adicionado com sucesso!' )
      } else {
        toast.error('Falha ao adicionar o cliente. Tente novamente mais tarde')
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
          Nome do cliente
        </label>
        <Input
          id='nome'
          name='nome'
          placeholder='Ex: Jonas Silva'
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
          placeholder='Ex: jonas@email.com'
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
          placeholder='(11) 9XXXX-XXXX'
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
          placeholder='000.000.000-00'
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
          placeholder='00000-000'
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
            placeholder='Rua exemplo 123'
            required
          />
        </div>

        <div className='flex flex-col gap-2'>
          <label htmlFor='sexo' className='text-sm font-semibold'>
            Sexo
          </label>
          <Select onValueChange={ ( value ) => setSexo( value as "M" | "F" )} value={ sexo }>
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
          placeholder='XX/XX/XXXX'
          required
        />
      </div>

      <Button
        type='submit'
        className='mt-4 w-full'
        disabled={ isLoading }
      >
        { isLoading ? 'Salvando...' : 'Salvar Cliente' }
      </Button>

      <DialogClose ref={ closeRef } className='hidden' />
    </form>
  )
}