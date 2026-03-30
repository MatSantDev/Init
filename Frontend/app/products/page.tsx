import { Product } from '@/types/product'
import { getProducts } from '@/utils/getProducts'

import { DataTable } from '@/components/ui/data-table'
import { columns } from '@/app/products/columns'
import { Logo } from '@/components/logo'

export default async function ProductsPage() {
  const products: Product[] = await getProducts()

  return (
    <main className='h-screen flex flex-col items-center pt-32 gap-12' >
      <section className='flex flex-col gap-5 items-center justify-center' >
        <Logo size='sm' />

        <h1 className='font-bold text-3xl' >
          Produtos
        </h1>
        <p className='text-lg' >
          Confira todos os produtos registrados em nosso sistema.
        </p>
      </section>
      <section className='w-full px-20 pt-5 pb-7' >
        <DataTable
          columns={ columns }
          data={ products }
        />
      </section>
    </main>
  )
}