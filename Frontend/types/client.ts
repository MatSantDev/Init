export interface Client {
  id: number
  nome: string
  email: string
  telefone: number
  cpf: string
  cep: string
  endereco: string
  sexo: 'M' | 'H'
  dataNascimento: Date
  criadoEm: Date
}