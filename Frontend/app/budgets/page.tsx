import { AlertTriangle } from 'lucide-react'

import { columns } from '@/app/budgets/columns'

import { Budget } from '@/types/budget'
import { Client } from '@/types/client'
import { Product } from '@/types/product'

import { getBudgets } from '@/utils/budgetsData'
import { getClients } from '@/utils/clientsData'
import { getProducts } from '@/utils/productsData'
import { Service } from '@/types/service'

import { DataTable } from '@/components/ui/data-table'
import { AddBudgetForm } from '@/components/budgets/addBudgetForm'
import { getServices } from '@/utils/servicesData'

export default async function BudgetsPage() {
  const budgets: Budget[] = await getBudgets()
  const clients: Client[] = await getClients()
  const products: Product[] = await getProducts()
  const services: Service[] = await getServices()

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
          <section className='w-full px-2 md:px-12 pt-5 pb-7' >
            <DataTable
              data={ budgets }
              columns={ columns }
              meta={ { clients, products, services } }
              modalContent={ <AddBudgetForm key={ clients[0].id } clients={ clients } /> }
              text='Adicionar novo orçamento'
            />
          </section>
        )
      }
    </main>
  )
}