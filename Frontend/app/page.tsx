import {
  CircleDollarSign,
  NotepadText,
  Package,
  Wrench,
  AlertCircle,
  Users,
} from 'lucide-react'

import { Card, CardProps } from '@/components/card'
import { BudgetsStatusPieChart, ClientsBudgetsChart } from '@/components/charts'

import { Budget } from '@/types/budget'
import { Product } from '@/types/product'
import { Service } from '@/types/service'
import { Client } from '@/types/client'

import { getProducts } from '@/utils/productsData'
import { getServices } from '@/utils/servicesData'
import { getBudgets } from '@/utils/budgetsData'
import { formatValue } from '@/utils/formatters'
import { getClients } from '@/utils/clientsData'

export default async function Home() {
  const products: Product[] = await getProducts()
  const services: Service[] = await getServices()
  const budgets: Budget[] = await getBudgets()
  const clients: Client[] = await getClients()

  const totalValue = budgets.reduce(( total, budget ) => {
    return total + budget.valorTotal;
  }, 0)

  const items: CardProps[] = [
    {
      text: 'Produtos',
      icon: Package,
      value: products.length,
    },
    {
      text: 'Serviços',
      icon: Wrench,
      value: services.length,
    },
    {
      text: 'Orçamentos',
      icon: NotepadText,
      value: budgets.length,
    },
    {
      text: 'Clientes',
      icon: Users,
      value: clients.length,
    },
    {
      text: 'Valor Total',
      icon: CircleDollarSign,
      value: formatValue( totalValue ),
    },
  ]

  const statusCount = budgets.reduce(( acc, budget ) => {
    const status = budget.status || 'PENDENTE'
    acc[ status] = ( acc[ status ] || 0) + 1
    return acc;
  }, {} as Record< string, number >);

  const statusPieChartData = [
    { status: "pendente", quantidade: statusCount["PENDENTE"] || 0, fill: "var(--color-pendente)" },
    { status: "concluido", quantidade: statusCount["CONCLUIDO"] || 0, fill: "var(--color-concluido)" },
    { status: "cancelado", quantidade: statusCount["CANCELADO"] || 0, fill: "var(--color-cancelado)" },
  ]

  const clientsCount = budgets.reduce(( acc, budget ) => {
    const clientName = typeof budget.cliente === 'object' ? budget.cliente.nome : 'Desconhecido'
    acc[ clientName ] = ( acc[ clientName ] || 0 ) + 1
    return acc;
  }, {} as Record< string, number > )

  const topClientsData = Object.entries( clientsCount )
    .map(( [ nome, quantidade ] ) => ( { nome, quantidade } ) )
    .sort(( a, b ) => b.quantidade - a.quantidade)
    .slice( 0, 5 )

  return (
    <main className='h-screen flex flex-col w-full px-12 gap-12' >
      <section className='flex flex-col gap-5 text-center md:text-left' >
        <h1 className='text-blue font-bold text-3xl' >
          Painel
        </h1>
        <p className='text-md md:text-lg' >
          Tenha uma visão geral do nosso sistema e confira os principais dados
        </p>
      </section>
      <section className='flex flex-col md:flex-row gap-2' >
        { items.map( ( item ) => (
          <Card
            key={ item.text }
            { ...item }
          />
        ) ) }
      </section>
      <section className='w-full flex flex-col md:flex-row items-center justify-between gap-2' >
        { budgets.length > 0 ? (
          <div className="w-full max-w-xl border rounded-xl p-6 bg-card">
            <h2 className="text-lg md:text-2xl text-center font-semibold mb-4">
              Orçamentos por Status
            </h2>
            <BudgetsStatusPieChart data={ statusPieChartData } />
          </div>
        ) : (
          <div className="flex flex-col items-center justify-center w-full max-w-xl border rounded-xl p-6 bg-card" >
            <AlertCircle />
            <h2 className='text-center text-lg' >
              Não foi possível carregar os dados dos orçamentos, tente novamente mais tarde.
            </h2>
          </div>
        ) }

        { clients.length > 0 ? (
          <div className="w-full border rounded-xl p-6 bg-card">
            <h2 className="text-lg md:text-2xl text-center font-semibold mb-4">
                Clientes com mais orçamentos
              </h2>
            <ClientsBudgetsChart data={ topClientsData } />
          </div>
        ) : (
          <div className="flex flex-col items-center justify-center w-full max-w-xl border rounded-xl p-6 bg-card" >
            <AlertCircle />
            <h2 className='text-center text-lg' >
              Não foi possível carregar os dados dos clientes, tente novamente mais tarde.
            </h2>
          </div>
        ) }

      </section>
    </main>
  )
}