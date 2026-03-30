"use client"

import { Product } from '@/types/product'
import { formatValue } from '@/utils/formatValue';
import { ColumnDef } from "@tanstack/react-table"

export const columns: ColumnDef< Product >[] = [
  {
    accessorKey: "nome",
    header: "Nome",
  },
  {
    accessorKey: "descricao",
    header: "Descrição",
  },
  {
    accessorKey: "preco",
    header: "Preço",
    cell: ( { row } ) => {
      const value = row.getValue<number>("preco");
      const result = formatValue(value);

      return (
        <p>
          { result }
        </p>
      );
    },
    
  },
]