"use client"

import { ArrowUpDown } from 'lucide-react';
import { ColumnDef } from "@tanstack/react-table"

import { Button } from '@/components/ui/button';
import { Budget } from '@/types/budget';

export const columns: ColumnDef< Budget >[] = [
  {
    accessorKey: "cliente",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={ () => column.toggleSorting(column.getIsSorted() === "asc") }
        >
          < p className='text-lg font-semibold' > Cliente </p>
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      )
    },
  },
  {
    accessorKey: "dataOrcamento",
    header: "Data do Orçamento",
  },
  {
    accessorKey: "observacao",
    header: "Observação",
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