'use client'

import {
  ColumnDef,
  flexRender,
  getCoreRowModel,
  useReactTable,
  getPaginationRowModel,
} from '@tanstack/react-table'
import {
  ArrowBigRight,
  ArrowBigLeft,
  ArrowBigRightDash,
  ArrowBigLeftDash,
} from 'lucide-react'
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table'
import { Button } from '@/components/ui/button'

interface DataTableProps< TData, TValue > {
  columns: ColumnDef<TData, TValue>[]
  data: TData[]
}

export function DataTable< TData, TValue >({
  columns,
  data,
}: DataTableProps<TData, TValue>) {
  const table = useReactTable({
    data,
    columns,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
  })

  return (
    <nav className='flex flex-col gap-2' >
      <div className='overflow-hidden rounded-md border'>
        <Table className='w-full' >
          <TableHeader className='font-bold text-lg text-center' >
            { table.getHeaderGroups().map((headerGroup) => (
              <TableRow key={ headerGroup.id } className='bg-blue hover:bg-blue/70 transition-all' >
                { headerGroup.headers.map((header) => {
                  return (
                    <TableHead
                      key={ header.id }
                      className='text-center font-bold'
                    >
                      { header.isPlaceholder
                        ? null
                        : flexRender( 
                            header.column.columnDef.header,
                            header.getContext()
                          )
                      }
                    </TableHead>
                  )
                }) }
              </TableRow>
            )) }
          </TableHeader>
          <TableBody>
            {
              table.getRowModel().rows.map(( row ) => (
                <TableRow
                  key={ row.id }
                  data-state={ row.getIsSelected() && 'selected' }
                >
                  { row.getVisibleCells().map(( cell ) => (
                    <TableCell
                      key={ cell.id }
                      className='text-center'
                    >
                      { flexRender(cell.column.columnDef.cell, cell.getContext())} 
                    </TableCell>
                  ))}
                </TableRow>
              ))
            }
          </TableBody>
        </Table>
      </div>
      <section className='w-full flex items-center justify-between rounded-xl border px-3 -py-2 text-center ' >
        <p className='text-md font-semibold' >
          { data.length } registros no total
        </p>

        <p className='flex items-center justify-center text-sm font-medium'>
          Página { table.getState().pagination.pageIndex + 1 } de { table.getPageCount() }
        </p>

        <div className='flex items-center justify-end space-x-2 py-4'>
          <Button
            variant='outline'
            size='sm'
            onClick={ () => table.setPageIndex(0) }
            disabled={ !table.getCanPreviousPage() }
          >
            <ArrowBigLeftDash />
          </Button>

          <Button
            variant='outline'
            size='sm'
            onClick={ () => table.previousPage() }
            disabled={ !table.getCanPreviousPage() }
          >
            <ArrowBigLeft />
          </Button>

          <Button
            variant='outline'
            size='sm'
            onClick={ () => table.nextPage() }
            disabled={ !table.getCanNextPage() }
          >
            <ArrowBigRight />
          </Button>

          <Button
            variant='outline'
            size='sm'
            onClick={ () => table.setPageIndex(table.getPageCount() - 1) }
            disabled={ !table.getCanNextPage() }
          >
            <ArrowBigRightDash />
          </Button>
        </div>
      </section>
    </nav>
  )
}