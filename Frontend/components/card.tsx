import { LucideIcon } from 'lucide-react'

export interface CardProps {
  text: string
  icon: LucideIcon
  value: number
}

export function Card( { text, icon: Icon, value }: CardProps ) {
  return (
    <div className='flex flex-col gap-3 p-5 w-full md:w-3/4 h-36 bg-accent rounded-xl' >

      <div className='flex items-center justify-between w-full' >
        <p className='text-xl font-semibold' >
          { text }
        </p>

        <Icon className='h-7 w-7 text-blue' />

      </div>

      <p className='font-bold text-3xl' >
        { value }
      </p>


    </div>
  )
}