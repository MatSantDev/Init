'use client'

import { ArrowUpDown } from 'lucide-react';
import { ColumnDef } from '@tanstack/react-table'

import { formatValue } from '@/utils/formatters';
import { Product } from '@/types/product';

import { Button } from '@/components/ui/button';
import { ProductActions } from '@/components/products/productActions';

export const columns: ColumnDef< Product >[] = [
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
    accessorKey: 'estoque',
    header: 'Estoque',
  },
  {
    id: 'actions',
    header: 'Ações',
    cell: ({ row }) => {
      const product = row.original

      return <ProductActions product={ product } />
    }
  }
]