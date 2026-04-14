export interface Client {
  id: number
  nome: string
  email: string
  telefone: number
  cpf: string
  cep: string
  endereco: string
  sexo: 'F' | 'M'
  dataNascimento: Date
  criadoEm: Date
}