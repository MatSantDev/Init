import Image from 'next/image';
import logo from '@/public/logo.png'

export function Logo( { size }: { size?: string } ) {
  let height
  let width
  let text

  switch ( size ) {
    case 'lg':
      height = 100
      width = 100
      text = 'text-5xl'
      break;
    case 'md':
      height = 70
      width = 70
      text = 'text-3xl'
      break;
    case 'sm':
      height = 50
      width = 50
      text = 'text-2xl'
      break;
    case 'xs':
      height = 30
      width = 30
      text = 'text-xl'
      break;
    default:
      height = 70
      width = 70
      text = 'text-3xl'
  }

  return (
    <a
      href='/'
      className='flex items-center justify-center gap-2 hover:scale-105 transition-all'
    >
      <Image
        src={ logo.src }
        alt='Logo'
        height={ height }
        width={ width }
        className='object-contain'
      />
      <h1 className={ ` ${ text } font-bold ` } >
        OrçamentoFly
      </h1>
    </a>
  )
}