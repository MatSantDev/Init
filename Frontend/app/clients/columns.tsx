'use client'

import { ArrowUpDown } from 'lucide-react';
import { ColumnDef } from '@tanstack/react-table'

import { Button } from '@/components/ui/button';
import { ClientActions } from '@/components/clients/clientActions';

import { formatValue } from '@/utils/formatValue';
import { formatDate } from '@/utils/formatDate';

import { Client } from '@/types/client';

export const columns: ColumnDef< Client >[] = [
  {
    accessorKey: 'nome',
    header: ({ column }) => {
      return (
        <Button
          variant='ghost'
          onClick={ () => column.toggleSorting(column.getIsSorted() === 'asc') }
        >
          < p className='text-lg font-semibold' > Nome </p>
          <ArrowUpDown className='ml-2 h-4 w-4' />
        </Button>
      )
    },
  },
  {
    accessorKey: 'email',
    header: 'Email',
  },
  {
    accessorKey: 'telefone',
    header: 'Telefone',
  },
  {
    accessorKey: 'cpf',
    header: 'CPF',
  },
  {
    accessorKey: 'cep',
    header: 'CEP',
  },
  {
    accessorKey: 'endereco',
    header: 'Endereco',
  },
  {
    accessorKey: 'sexo',
    header: 'Sexo',
  },
  {
    accessorKey: 'dataNascimento',
    header: 'Data de Nascimento',
    cell: ( { row } ) => {
      const date = row.getValue<string>('dataNascimento');
      const result = formatDate( date );

      return (
        <p>
          { result }
        </p>
      );
    },
  },
  {
    accessorKey: 'criadoEm',
    header: 'Criado Em',
    cell: ( { row } ) => {
      const date = row.getValue<string>('criadoEm');
      const result = formatDate( date );

      return (
        <p>
          { result }
        </p>
      );
    },
  },
  {
    accessorKey: 'Total de Orçamentos',
    cell: ({ row }) => {
      return 2
    }
  },
  {
    id: 'actions',
    header: 'Ações',
    cell: ({ row }) => {
      const client = row.original

      return <ClientActions client={ client } />
    }
  }
]