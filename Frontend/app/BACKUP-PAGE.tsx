import { Logo } from '@/components/logo';
import { Copyright } from 'lucide-react';

export default async function Home() {
  const items = [
    {
      name: 'Produtos',
      imageUrl: 'https://cdn-icons-png.freepik.com/256/18188/18188249.png?semt=ais_white_label',
      href: '/products',
    },
    {
      name: 'Serviços',
      imageUrl: 'https://cdni.iconscout.com/illustration/premium/thumb/equipe-de-limpeza-com-escritorio-de-limpeza-de-equipamentos-profissionais-illustration-svg-download-png-5953208.png',
      href: '/services'
    },
    {
      name: 'Orçamentos',
      imageUrl: 'https://cdni.iconscout.com/illustration/premium/thumb/orcamento-illustration-svg-download-png-5988761.png',
      href: '/budgets'
    },
  ]

  return (
    <main className='h-screen flex flex-col items-center justify-center gap-12' >
      <section className='flex flex-col gap-5 items-center justify-center' >
        <Logo />

        <h1 className='font-bold text-3xl text-center' >
          Gerencie seus orçamentos de forma prática com a OrçamentoFly
        </h1>
      </section>

      <section className='w-full flex items-center justify-center gap-10' >
        { items.map( card => (
          <a
            key={ card.name }
            className='bg-blue rounded-xl p-5 items-center justify-center gap-3 hover:scale-110 transition-all'
            href={ card.href }
          >
            <img
              src={ card.imageUrl }
              alt="Illustrations"
              className='h-60 w-60'
            />
            <p className='text-center w-full mt-2 font-bold text-2xl' >
              { card.name }
            </p>
          </a>
        ) ) }
      </section>

      <p className='flex items-center justify-center gap-2 text-sm opacity-80 font-semibold' >
        <Copyright size={ 18 } /> OrçamentoFly todos os direitos reservados
      </p>

    </main>
  );
}
