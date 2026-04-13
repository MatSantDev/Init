'use client'

import Link from 'next/link'
import { usePathname } from 'next/navigation'

import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarHeader,
} from "@/components/ui/sidebar"

import { Logo } from '@/components/logo'
import { Handshake, PiggyBank, ShoppingBag, Users } from 'lucide-react'

export function AppSidebar() {
  const pathname = usePathname()

  const items = [
    {
      name: 'Produtos',
      icon: ShoppingBag,
      href: '/products',
    },
    {
      name: 'Serviços',
      icon: Handshake,
      href: '/services'
    },
    {
      name: 'Orçamentos',
      icon: PiggyBank,
      href: '/budgets'
    },
    {
      name: 'Clientes',
      icon: Users,
      href: '/clients'
    },
  ]

  return (
    <Sidebar className='flex flex-col justify-between py-7 bg-background' >
      <SidebarHeader className='mb-7 bg-background' >
        <Logo size='xs' />
      </SidebarHeader>

      <SidebarContent className=' flex flex-col gap-3 items-start bg-background px-3' >
        {
          items.map( item => {
            const isActive = pathname.startsWith( item.href )

            return (
              <SidebarGroup
                key={ item.name }
                className={ `flex bg-background justify-center items-start hover:scale-105 hover:text-blue hover:bg-blue/10 transition-all pl-4 rounded-xl
                  ${ isActive && 'text-blue font-bold' }
                ` }
              >
                <Link
                  href={ item.href }
                  className='flex items-center justify-center gap-2'
                >
                  <item.icon className='h-6 w-6' strokeWidth={ isActive ? 2.5 : 1.5 } />
                  <p className='text-lg' >
                    { item.name }
                  </p>
                </Link>
              </SidebarGroup>
            )
          } )
        }
      </SidebarContent>

      <SidebarFooter className='bg-background' />
    </Sidebar>
  )
}