import { AlertTriangle } from 'lucide-react'

import { Client } from '@/types/client'
import { getBudgets } from '@/utils/budgetsData'

import { DataTable } from '@/components/ui/data-table'
import { AddBudgetForm } from '@/components/budgets/addBudgetForm'
import { columns } from '@/app/clients/columns'
import { getClients } from '@/utils/clientsData'

export default async function ClientsPage() {
  // const clients: Client[] = Array.from({ length: 100 }, ( _, i ) => ({
  //   id: i + 1,
  //   nome: `Cliente${i + 1}`,
  //   email: `cliente${i + 1}@email.com`,
  //   telefone: 1231232121,
  //   cpf: `${String(i).padStart(11, "0")}`,
  //   cep: '123123',
  //   endereco: 'bla bla bla',
  //   sexo: 'M',
  //   dataNascimento: new Date(),
  //   criadoEm: new Date(),
  // }));

  const clients: Client[] = await getClients()

  console.log('CLIENTS')
  console.log( clients )

  return (
    <main className='h-screen flex flex-col items-center text-center pt-12 gap-12' >
      <section className='flex flex-col gap-5 items-center justify-center' >
        <h1 className='text-blue font-bold text-3xl' >
          Clientes
        </h1>
        <p className='text-md md:text-lg' >
          Confira todos os clientes registrados em nosso sistema.
        </p>
      </section>
      {
        clients.length === 0 ? (
          <div className='flex flex-col gap-2 items-center justify-center mt-32' >
            <AlertTriangle size={ 72 } />
            <p className='text-sm md:text-lg font-semibold' >
              Não foi possível trazer os clientes, por favor tente novamente mais tarde
            </p>
          </div>
        ) : (
          <section className='w-full px-2 md:px-20 pt-5 pb-7' >
            <DataTable
              data={ clients }
              columns={ columns }
              modalContent={ <AddBudgetForm /> }
              text='Adicionar novo cliente'
            />
          </section>
        )
      }
    </main>
  )
}