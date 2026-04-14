export function formatPhone( value: string ) {
  value = value.replace(/\D/g, '')

  value = value.slice(0, 11)

  if ( value.length <= 2 ) {
    return `(${value}`
  }

  if ( value.length <= 7 ) {
    return `(${value.slice(0, 2)}) ${value.slice(2)}`
  }

  return `(${value.slice(0, 2)}) ${value.slice(2, 7)}-${value.slice(7)}`
}

export function formatValue( value: number ): string {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  }).format(value);
}

export function formatText( text: string ) {
  return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase()
}

export function formatDate( date: Date | string ) {
  if ( !date ) return '-';
  const d = new Date(date);

  return new Intl.DateTimeFormat('pt-BR', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    timeZone: 'UTC',
  }).format(d);
};

export const formatDateForInput = ( date: Date | string ) => {
  const d = new Date(date);
  const month = `0${d.getMonth() + 1}`.slice(-2);
  const day = `0${d.getDate()}`.slice(-2);
  const year = d.getFullYear();
  return `${year}-${month}-${day}`;
}

export function formatCPF( value: string ) {
  value = value.replace(/\D/g, '')
  value = value.slice(0, 11)

  if ( value.length <= 3 ) return value
  if ( value.length <= 6 ) {
    return `${value.slice(0, 3)}.${value.slice(3)}`
  }
  if ( value.length <= 9 ) {
    return `${ value.slice(0, 3) }.${ value.slice(3, 6) }.${ value.slice(6) }`
  }

  return `${ value.slice(0, 3) }.${ value.slice(3, 6) }.${ value.slice(6, 9) }-${ value.slice(9) }`
}

export function formatCEP( value: string ) {
  value = value.replace(/\D/g, '')
  value = value.slice(0, 8)

  if ( value.length <= 5 ) return value

  return `${ value.slice(0, 5) }-${ value.slice(5) }`
}