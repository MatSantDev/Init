import { AlertTriangle } from 'lucide-react'

import { Budget } from '@/types/budget'
import { Client } from '@/types/client'

import { getBudgets } from '@/utils/budgetsData'
import { getClients } from '@/utils/clientsData'

import { DataTable } from '@/components/ui/data-table'
import { AddBudgetForm } from '@/components/budgets/addBudgetForm'
import { columns } from '@/app/budgets/columns'
// import { getColumns } from '@/app/budgets/columns'

export default async function BudgetsPage() {
  const budgets: Budget[] = await getBudgets()
  const clients: Client[] = await getClients()

  return (
    <main className='h-screen flex flex-col items-center text-center pt-12 gap-12' >
      <section className='flex flex-col gap-5 items-center justify-center' >
        <h1 className='text-blue font-bold text-3xl' >
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
              data={ budgets }
              columns={ columns }
              modalContent={ <AddBudgetForm clients={ clients } /> }
              text='Adicionar novo orçamento'
            />
          </section>
        //   <section className='w-full px-2 md:px-20 pt-5 pb-7' >
        //   <DataTable
        //     data={ budgets }
        //     // Chame a função passando os clientes aqui:
        //     columns={ getColumns(clients) }
        //     modalContent={ <AddBudgetForm clients={clients} /> }
        //     text='Adicionar novo orçamento'
        //   />
        // </section>
        )
      }
    </main>
  )
}