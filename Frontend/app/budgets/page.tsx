import { Logo } from '@/components/logo';

export default function BudgetsPage() {
  return (
    <main className='h-screen flex flex-col items-center pt-32 gap-12' >
      <section className='flex flex-col gap-5 items-center justify-center' >
        <Logo size='sm' />

        <h1 className='font-bold text-3xl' >
          Orçamentos
        </h1>
        <p className='text-lg' >
          Confira todos os orçamentos registrados em nosso sistema.
        </p>
      </section>
    </main>
  )
}