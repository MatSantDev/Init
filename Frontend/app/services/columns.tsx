'use client'

import { ArrowUpDown } from 'lucide-react';
import { ColumnDef } from '@tanstack/react-table'

import { Button } from '@/components/ui/button';
import { Service } from '@/types/service';
import { formatValue } from '@/utils/formatValue';

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
    header: 'Ações',
    cell: () => {
      return (
        <div className='flex items-center justify-center gap-2' >

            <Button
              size='xs'
              variant='outline'
              onClick={ () => alert('editou') }
            >
              Editar
            </Button>

            <Button
              size='xs'
              variant='destructive'
              onClick={ () => alert('excluiu') }
            >
              Excluir
            </Button>
        </div>
      )
    }
  }
]