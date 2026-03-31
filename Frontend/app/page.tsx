import {
  CircleDollarSign,
  NotepadText,
  Package,
  Wrench,
} from 'lucide-react'

import { Card, CardProps } from '@/components/card'
import { getProducts } from '@/utils/productsData'
import { getServices } from '@/utils/servicesData'

export default async function Home() {
  const products = await getProducts()
  const services = await getServices()
  const budgets = await getServices()

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
      value: 0,
    },
  ]

  return (
    <main className='h-screen flex flex-col w-full px-12 gap-12' >
      <section className='flex flex-col gap-5 text-center md:text-left' >
        <h1 className='font-bold text-3xl' >
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
