'use client'

import { ArrowUpDown } from 'lucide-react';
import { ColumnDef } from '@tanstack/react-table'

import { Button } from '@/components/ui/button';
import { ClientActions } from '@/components/clients/clientActions';

import { formatDate } from '@/utils/formatters';

import { Client } from '@/types/client';

export const columns: ColumnDef< Client >[] = [
  {
    accessorKey: 'id',
    header: 'ID',
    filterFn: ( row, id, filterValue ) => {
      if ( !filterValue ) return true
      
      return Number( row.getValue( id ) ) === Number( filterValue )
    },
    cell: ({ row }) => {
      return (
        <p className='font-bold text-muted-foreground'>
          #{ row.getValue('id') }
        </p>
      )
    }
  },
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
    accessorKey: 'totalBudgets',
    header: 'Total de Orçamentos',
    cell: ({ row }) => {
      const clientBudgets = row.getValue<string>('totalBudgets')
      return ( <p> { clientBudgets } </p> )
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