import {
  CircleDollarSign,
  NotepadText,
  Package,
  Wrench,
} from 'lucide-react'

import { Card, CardProps } from '@/components/card'

import { getProducts } from '@/utils/productsData'
import { getServices } from '@/utils/servicesData'
import { getBudgets } from '@/utils/budgetsData'
import { formatValue } from '@/utils/formatValue'

export default async function Home() {
  const products = await getProducts()
  const services = await getServices()
  const budgets = await getBudgets()

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
      text: 'Valor Total',
      icon: CircleDollarSign,
      value: formatValue( totalValue ),
    },
  ]

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
    </main>
  )
}