'use client'

import { ReactNode, useState } from 'react'
import {
  ColumnDef,
  flexRender,
  getCoreRowModel,
  useReactTable,
  getPaginationRowModel,
  getFilteredRowModel,
  SortingState,
  getSortedRowModel,
} from '@tanstack/react-table'
import {
  ArrowBigRight,
  ArrowBigLeft,
  ArrowBigRightDash,
  ArrowBigLeftDash,
  Plus,
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
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import { Input } from '@/components/ui/input' 

interface DataTableProps< TData, TValue > {
  columns: ColumnDef<TData, TValue>[]
  data: TData[]
  modalContent?: ReactNode,
  text?: string
  meta?: any
}

export function DataTable< TData, TValue >({
  columns,
  data,
  modalContent,
  text,
  meta,
}: DataTableProps<TData, TValue>) {
  const [ globalFilter, setGlobalFilter ] = useState('')
  const [ sorting, setSorting ] = useState<SortingState>([])

  const table = useReactTable({
    data,
    columns,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
    getSortedRowModel: getSortedRowModel(),
    onSortingChange: setSorting,
    getFilteredRowModel: getFilteredRowModel(),
    state: {
      globalFilter,
      sorting,
    },
    onGlobalFilterChange: setGlobalFilter,
    meta,
  })

  return (
    <nav className='flex flex-col gap-4' >
      <div className='flex items-center justify-between w-full' >
        <Input
          placeholder='Pesquisar...'
          value={ globalFilter ?? '' }
          onChange={ ( event ) => setGlobalFilter(String( event.target.value ) ) }
          className='w-3/4'
        />

        { modalContent ? (
          <Dialog>
            <DialogTrigger asChild >
              <div>
                 <Button
                  variant='outline'
                  size='lg'
                  className='p-3'
                >
                  <Plus size={ 32 } />
                  <p className='hidden md:flex text-lg' >
                    { text || 'Adicionar novo registro' }
                  </p>
                </Button>
              </div>
            </DialogTrigger>
            <DialogContent>
              <DialogHeader>
                <DialogTitle className='text-xl font-semibold' >
                  { text || 'Adicionar novo registro' }
                </DialogTitle>
              </DialogHeader>
              { modalContent }
            </DialogContent>
          </Dialog>
        ) : null }
      </div>

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
            { table.getRowModel().rows?.length ? (
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
            ) : (
              <TableRow>
                <TableCell colSpan={columns.length} className="h-24 text-center">
                  Nenhum resultado encontrado.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>

      <section className='w-full flex flex-col md:flex-row items-center justify-center md:justify-between rounded-xl border px-3 py-2 md:-py-2 text-center gap-2 md:gap-0' >
        <p className='text-md font-semibold' >
          { data.length } registros no total
        </p>

        <p className='flex items-center justify-center text-md md:text-sm font-medium'>
          Página { table.getState().pagination.pageIndex + 1 } de { table.getPageCount() }
        </p>

        <div className='flex items-center justify-end space-x-2 py-0 md:py-4'>
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