'use client'

import { ArrowUpDown } from 'lucide-react';
import { ColumnDef } from '@tanstack/react-table'

import { Button } from '@/components/ui/button';
import { BudgetActions } from '@/components/budgets/budgetActions';

import { Budget } from '@/types/budget';
import { Client } from '@/types/client';

import { formatValue } from '@/utils/formatValue';
import { formatDate } from '@/utils/formatDate';

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
    id: 'actions',
    header: 'Ações',
    cell: ({ row }) => {
      const budget = row.original

      return <BudgetActions budget={ budget } clients={ [] } />
    }
  }
]

// export const getColumns = (clients: Client[]): ColumnDef<Budget>[] => [
//   {
//     accessorKey: 'cliente',
//     header: ({ column }) => {
//       return (
//         <Button
//           variant='ghost'
//           onClick={ () => column.toggleSorting(column.getIsSorted() === 'asc') }
//         >
//           < p className='text-lg font-semibold' > Cliente </p>
//           <ArrowUpDown className='ml-2 h-4 w-4' />
//         </Button>
//       )
//     },
//     cell: ( { row } ) => {
//       const budget = row.original
//       return <p>{ budget.cliente.nome }</p>;
//     },
//   },
//   {
//     accessorKey: 'dataOrcamento',
//     header: 'Data do Orçamento',
//     cell: ( { row } ) => {
//       const date = row.getValue<string>('dataOrcamento');
//       return <p>{ formatDate( date ) }</p>;
//     },
//   },
//   {
//     accessorKey: 'observacao',
//     header: 'Observação',
//   },
//   {
//     accessorKey: 'valorTotal',
//     header: 'Valor Total',
//     cell: ( { row } ) => {
//       const value = row.getValue<number>('valorTotal');
//       return <p>{ formatValue(value) }</p>;
//     },
//   },
//   {
//     id: 'actions',
//     header: 'Ações',
//     cell: ({ row }) => {
//       const budget = row.original

//       // 3. Agora você tem acesso aos 'clients' para passar como prop!
//       return <BudgetActions budget={ budget } clients={ clients } />
//     }
//   }
// ]