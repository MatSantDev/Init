import { Client } from '@/types/client'

export interface Budget {
  id: number
  cliente: Client
  dataOrcamento: Date
  observacao: string
  valorTotal: number
}