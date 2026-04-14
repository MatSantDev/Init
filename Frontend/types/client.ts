export interface Client {
  id: number
  nome: string
  email: string
  telefone: string
  cpf: string
  cep: string
  endereco: string
  sexo: 'M' | 'F'
  dataNascimento: Date
  criadoEm: Date
}