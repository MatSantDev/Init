import { Budget } from '@/types/budget';

export async function getBudgets() {
  try {
    const res = await fetch('http://localhost:8080/orcamentos/consultarTodos')
    let data: Budget[]

    if ( !res.ok ) return data = []

    data = await res.json()

    return data;

  } catch ( err ) {
    console.log( err )
    return []
  }

}