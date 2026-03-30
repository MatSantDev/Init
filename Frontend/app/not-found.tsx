import { Button } from '@/components/ui/button'
import Link from 'next/link'
 
export default function NotFound() {
  return (
    <main className='h-screen flex flex-col items-center justify-center gap-4' >
      <h1 className='text-5xl font-bold' >
        404 Página não encontrada
      </h1>
      <p className='text-lg text-white/85' >
        Parece que a página que você esta tentando acessar não foi encontrada, tente novamente mais tarde.
      </p>
      <Button size='lg' >
        <Link href="/">
          Voltar
        </Link>
      </Button>
    </main>
  )
}