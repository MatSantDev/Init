export interface BudgetItem {
  id: number
  orcamento_id: number
  descricao: string
  tipoOrcamento: 'PRODUTO' | 'SERVICO'
  quantidade: number
  valorUnitario: number
  subTotal: number // qnt x valorUnitario
  produto_id: number | null
  servico_id: number | null
}
