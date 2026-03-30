export async function getProducts() {
  try {
    const res = await fetch('http://localhost:8080/produtos/consultarTodos')
    let data

    if ( !res.ok ) return data = []

    data = await res.json()

    return data;
  } catch ( err ) {
    console.log( err )
    return []
  }

}