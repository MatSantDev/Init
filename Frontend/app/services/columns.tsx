'use client'

import { ArrowUpDown } from 'lucide-react';
import { ColumnDef } from '@tanstack/react-table'

import { Button } from '@/components/ui/button';
import { Service } from '@/types/service';
import { formatValue } from '@/utils/formatValue';
import { ServiceActions } from '@/components/services/serviceActions';

export const columns: ColumnDef< Service >[] = [
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
    accessorKey: 'descricao',
    header: 'Descrição',
  },
  {
    accessorKey: 'valorUnitario',
    header: 'Preço',
    cell: ( { row } ) => {
      const value = row.getValue<number>('valorUnitario');
      const result = formatValue(value);

      return (
        <p>
          { result }
        </p>
      );
    },
  },
  {
    id: 'actions',
    header: 'Ações',
    cell: ({ row }) => {
      const service = row.original

      return <ServiceActions service={ service } />
    }
  }
]