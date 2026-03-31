import type { Metadata } from "next";
import { Inter, Geist } from "next/font/google";

import "./globals.css";
import { cn } from "@/lib/utils";

import { SidebarProvider, SidebarTrigger } from '@/components/ui/sidebar';
import { AppSidebar } from '@/components/app-sidebar';
import { Toaster } from "@/components/ui/sonner"

const geist = Geist({subsets:['latin'],variable:'--font-sans'});

const getInter = Inter({
  variable: "--font-inter",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "OrçamentoFly",
  description: "Sistema de Controle de Orçamentos",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html
      lang="pt-BR"
      className={ cn("h-full", "antialiased", getInter.variable, "font-sans", geist.variable, "dark") }
    >
      <body className="min-h-full flex flex-col">
        <SidebarProvider>
          <AppSidebar />
          <main className='w-full' >
            <SidebarTrigger />
            { children }
          </main>
          <Toaster />
        </SidebarProvider>
      </body>
    </html>
  );
}
