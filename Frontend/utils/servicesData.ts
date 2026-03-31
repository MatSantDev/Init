import { Service } from '@/types/service';

export async function getServices() {
  try {
    const res = await fetch('http://localhost:8080/servicos/consultarTodos')
    let data: Service[]

    if ( !res.ok ) return data = []

    data = await res.json()

    return data;

  } catch ( err ) {
    console.log( err )
    return []
  }

}