'use client'

import { useEffect, useState } from 'react';
import { ArrowUpDown } from 'lucide-react';
import { ColumnDef } from '@tanstack/react-table'

import { Button } from '@/components/ui/button';
import { BudgetActions } from '@/components/budgets/budgetActions';

import { Budget } from '@/types/budget';
import { Client } from '@/types/client';
import { Product } from '@/types/product';
import { Service } from '@/types/service';

import { formatValue, formatDate, formatText } from '@/utils/formatters';
import { getBudgetItems } from '@/utils/budgetItemData';

function BudgetItemsCountCell( { budgetId }: { budgetId: number } ) {
  const [ count, setCount ] = useState<number | null>(null);

  useEffect(() => {
    async function fetchCount() {
      try {
        const items = await getBudgetItems( budgetId )
        setCount(items.length)
      } catch (error) {
        setCount(0);
      }
    }
    fetchCount();
  }, [budgetId]);

  if ( count === null ) {
    return <span className="text-xs text-muted-foreground animate-pulse">Buscando...</span>;
  }

  return (
    <span className="font-medium">
      { count } { count === 1 ? 'item' : 'itens' }
    </span>
  );
}

export const columns: ColumnDef< Budget >[] = [
  {
    accessorKey: 'cliente',
    header: ({ column }) => {
      return (
        <Button
          variant='ghost'
          onClick={ () => column.toggleSorting(column.getIsSorted() === 'asc') }
        >
          < p className='text-lg font-semibold' > Cliente </p>
          <ArrowUpDown className='ml-2 h-4 w-4' />
        </Button>
      )
    },
    cell: ( { row } ) => {
      const budget = row.original

      return (
        <p>
          { budget.cliente.nome }
        </p>
      );
    },
  },
  {
    accessorKey: 'dataOrcamento',
    header: 'Data do Orçamento',
    cell: ( { row } ) => {
      const date = row.getValue<string>('dataOrcamento');
      const result = formatDate( date );

      return (
        <p>
          { result }
        </p>
      );
    },
  },
  {
    accessorKey: 'observacao',
    header: 'Observação',
  },
  {
    accessorKey: 'valorTotal',
    header: 'Valor Total',
    cell: ( { row } ) => {
      const value = row.getValue<number>('valorTotal');
      const result = formatValue(value);

      return (
        <p>
          { result }
        </p>
      );
    },
  },
  {
    accessorKey: 'status',
    header: ({ column }) => {
      return (
        <Button
          variant='ghost'
          onClick={ () => column.toggleSorting(column.getIsSorted() === 'asc') }
        >
          < p className='text-lg font-semibold' > Status </p>
          <ArrowUpDown className='ml-2 h-4 w-4' />
        </Button>
      )
    },
    cell: ({ row }) => {
      const status = row.getValue<string>('status')
      let color

      if ( status === 'CONCLUIDO' ) color = 'bg-green-500'
      if ( status === 'PENDENTE' ) color = 'bg-yellow-500'
      if ( status === 'CANCELADO' ) color = 'bg-red-500'

      return (
        <div className='flex items-center justify-center gap-2' >
          <div className={`h-2 w-2 rounded-full ${ color } `} />
          <p>
            { formatText( status ) }
          </p>
        </div>
      )
    },
  },
  {
    accessorKey: 'totalItemsBudgets',
    header: 'Items no Orçamento',
    cell: ( { row } ) => {
      const budgetId = row.original.id; 

      return (
        <BudgetItemsCountCell budgetId={ budgetId } />
      )
    },
  },
  {
    id: 'actions',
    header: 'Ações',
    cell: ({ row, table }) => {
      const budget = row.original
      const clients = ( table.options.meta as any )?.clients as Client[]
      const products = ( table.options.meta as any )?.products as Product[]
      const services = ( table.options.meta as any )?.services as Service[]

      return <BudgetActions budget={ budget } clients={ clients } products={ products } services={ services } />
    }
  }
]
