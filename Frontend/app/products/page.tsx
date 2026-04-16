import { AlertTriangle } from 'lucide-react'

import { Product } from '@/types/product'
import { getProducts } from '@/utils/productsData'

import { DataTable } from '@/components/ui/data-table'
import { AddProductForm } from '@/components/products/addProductForm'
import { columns } from '@/app/products/columns'

export default async function ProductsPage() {
  const products: Product[] = await getProducts()

  return (
    <main className='h-screen w-full flex flex-col items-center text-center pt-12 gap-12' >
      <section className='flex flex-col gap-5 items-center justify-center' >
        <h1 className='text-blue font-bold text-3xl' >
          Produtos
        </h1>
        <p className='text-md md:text-lg' >
          Confira todos os produtos registrados em nosso sistema.
        </p>
      </section>
      {
        products.length === 0 ? (
          <div className='flex flex-col gap-2 items-center justify-center mt-32' >
            <AlertTriangle size={ 72 } />
            <p className='text-sm md:text-lg font-semibold' >
              Não foi possível trazer os produtos, por favor tente novamente mais tarde
            </p>
          </div>
        ) : (
        <section className='w-full px-2 md:px-12 pt-5 pb-7' >
          <DataTable
            columns={ columns }
            data={ products }
            modalContent={ <AddProductForm /> }
            text='Adicionar novo produto'
          />
        </section>
        )
      }
    </main>
  )
}