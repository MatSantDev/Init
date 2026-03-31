import { AlertTriangle } from 'lucide-react'

import { DataTable } from '@/components/ui/data-table'
import { getBudgets } from '@/utils/budgetsData'

import { columns } from './columns'

export default async function ProductsPage() {
  const budgets = await getBudgets()

  return (
    <main className='h-screen flex flex-col items-center text-center pt-12 gap-12' >
      <section className='flex flex-col gap-5 items-center justify-center' >
        <h1 className='font-bold text-3xl' >
          Orçamentos
        </h1>
        <p className='text-md md:text-lg' >
          Confira todos os orçamentos registrados em nosso sistema.
        </p>
      </section>
      {
        budgets.length === 0 ? (
          <div className='flex flex-col gap-2 items-center justify-center mt-32' >
            <AlertTriangle size={ 72 } />
            <p className='text-sm md:text-lg font-semibold' >
              Não foi possível trazer os orçamentos, por favor tente novamente mais tarde
            </p>
          </div>
        ) : (
          <section className='w-full px-2 md:px-20 pt-5 pb-7' >
            <DataTable
              columns={ columns }
              data={ budgets }
            />
          </section>
        )
      }
    </main>
  )
}