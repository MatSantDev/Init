import { useMemo, useRef, useState } from 'react'
import { toast } from 'sonner'

import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogClose,
  DialogDescription,
} from '@/components/ui/dialog'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'

import { Service } from '@/types/service'
import { Product } from '@/types/product'

import { formatValue, formatText } from '@/utils/formatters'
import { addBudgetItem } from '@/utils/budgetItemData'

interface AddItemToBudgetModalProps {
  products: Product[]
  services: Service[]
  budgetId: number
  open: boolean
  onOpenChange: ( open: boolean ) => void
  onSuccess: () => void
}

export function AddItemToBudgetModal( {
  products,
  services,
  budgetId,
  open,
  onOpenChange,
  onSuccess,
}: AddItemToBudgetModalProps ) {
  const [ isLoading, setIsLoading ] = useState(false)
  const closeRef = useRef<HTMLButtonElement>(null)

  const [ itemType, setItemType ] = useState<'PRODUTO' | 'SERVICO' | ''>('' )
  const [ selectedItemId, setSelectedItemId ] = useState<string>('')
  const [ quantity, setQuantity ] = useState<number>(1)

  const itemSelected = useMemo(() => {
    if ( !selectedItemId || !itemType ) return null;

    if ( itemType === 'PRODUTO' ) {
      return products.find(p => p.id.toString() === selectedItemId )
    } else if ( itemType === 'SERVICO' ) {
      return services.find(s => s.id.toString() === selectedItemId)
    }

    return null;
  }, [ selectedItemId, itemType, products, services ] )

  const itemPrice = itemSelected?.valorUnitario || 0
  const total = itemPrice * quantity

  async function handleSubmit( e: React.FormEvent<HTMLFormElement> ) {
    e.preventDefault()

    if ( !selectedItemId || !itemType ) {
      toast.error('Por favor, selecione um tipo e um item.')
      return
    }

    if ( itemType === 'PRODUTO' && itemSelected ) {
      const product = itemSelected as Product

      console.log("PRODUTO SELECIONADO: ", product)
      
      if ( product.estoque === 0 ) {
        toast.error('Este produto está fora de estoque no momento!');
        return;
      }

      if ( quantity > product.estoque ) {
        toast.error(`Estoque insuficiente! Temos apenas ${ product.estoque } unidade(s) disponível(is).`);
        return;
      }
    }

    setIsLoading( true )

    const formData = new FormData()

    formData.append( 'orcamentoId', String( budgetId ) )
    formData.append( 'tipoOrcamentoItem', itemType )
    formData.append( 'quantidade', String( quantity ) )
    formData.append( 'valorUnitario', String( itemPrice ) )
    formData.append( 'subtotal', String( total ) )
    
    if ( itemSelected?.nome ) {
      formData.append('descricao', itemSelected.nome)
    }

    if ( itemType === 'PRODUTO') {
      formData.append('produto_id', selectedItemId )
    } else if ( itemType === 'SERVICO' ) {
      formData.append('servico_id', selectedItemId )
    }

    try {
      const result = await addBudgetItem( formData, budgetId )

     if ( result.success ) {
        if ( closeRef.current ) {
          closeRef.current.click()
        }
        toast.success( 'Item adicionado ao orçamento com sucesso!' )
      } else {
        setIsLoading( false )
        toast.error('Falha ao adicionar o item ao orçamento. Tente novamente mais tarde')
        console.error('Erro do servidor:', result.error )
      }

    } catch ( err ) {
      console.error('Erro ao adicionar item:', err )
      toast.error('Falha ao adicionar o item.')
      setIsLoading(false)
    }
  }

  return (
    <Dialog open={ open } onOpenChange={ onOpenChange }>
      <DialogContent className='sm:max-w-md'>

        <DialogHeader>
          <DialogTitle>
            Adicionar Itens ao Orçamento
          </DialogTitle>
          <DialogDescription>
            Você pode adicionar Produtos e Serviços em um orçamento.
          </DialogDescription>
        </DialogHeader>

        <form onSubmit={ handleSubmit } className='flex flex-col gap-4 py-4'>
          <div className='flex gap-4'>
            
            <div className='flex flex-col gap-2 w-1/3'>
              <label className='text-sm font-semibold'>
                Tipo de item
              </label>
              <Select
                value={ itemType }
                onValueChange={ ( val: 'PRODUTO' | 'SERVICO' ) => {
                  setItemType(val )
                  setSelectedItemId('')
                  setQuantity(1)
                }}
              >
                <SelectTrigger>
                  <SelectValue placeholder='Tipo' />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value='PRODUTO'>Produto</SelectItem>
                  <SelectItem value='SERVICO'>Serviço</SelectItem>
                </SelectContent>
              </Select>
            </div>

            <div className='flex flex-col gap-2 w-2/3'>
              <label className='text-sm font-semibold'>
                Selecionar { itemType === 'PRODUTO' ? 'Produto' : itemType === 'SERVICO' ? 'Serviço' : 'Item' }
              </label>
              
              <Select
                key={ itemType }
                value={ selectedItemId }
                onValueChange={ setSelectedItemId }
                disabled={ !itemType }
              >
                <SelectTrigger>
                  <SelectValue placeholder={ itemType ? 'Selecione...' : 'Escolha o tipo antes' } />
                </SelectTrigger>
                <SelectContent>
                  <SelectGroup>
                    { itemType === 'PRODUTO' && products?.map( p => (
                        <SelectItem
                          key={ `prod-${ p.id }` }
                          value={ p.id.toString() }
                          disabled={ p.estoque === 0 }
                        >
                          { p.nome } { p.estoque === 0 ? '(Sem estoque)' : `(Estoque: ${p.estoque})` }
                        </SelectItem>
                    ))}
                    
                    { itemType === 'SERVICO' && services?.map( s => (
                        <SelectItem key={ `serv-${ s.id }` } value={ s.id.toString() }>
                          { s.nome }
                        </SelectItem>
                    ))}
                  </SelectGroup>
                </SelectContent>
              </Select>
            </div>
          </div>

          <div className='flex gap-4 items-end' >
            <div className='flex flex-col gap-2 w-1/3'>
              <label className='text-sm font-semibold'>
                Quantidade
              </label>
              <Input
                type='number'
                min='1'
                value={ itemType === 'SERVICO' ? 1 : quantity }
                onChange={ ( e ) => setQuantity(Number( e.target.value ) ) }
                required
                disabled={ !selectedItemId || itemType === 'SERVICO' }
              />
            </div>

            <div className='flex flex-col gap-2 w-1/3'>
              <label className='text-sm font-semibold whitespace-nowrap'>
                Preço do { itemType ? formatText( itemType ) : 'Item' }
              </label>
              <p className='h-10 py-2'>
                { formatValue( itemPrice ) }
              </p>
            </div>
          </div>

          <div className='flex gap-2 items-center'>
              <span className='text-muted-foreground text-sm font-semibold'>
                Subtotal:
              </span>
              <span className='font-bold text-lg text-blue-600'>
                { formatValue( total ) }
              </span>
          </div>

          <Button type='submit' className='mt-4 w-full' disabled={ isLoading || !selectedItemId }>
            { isLoading ? 'Adicionando...' : 'Adicionar ao Orçamento' }
          </Button>

          <DialogClose ref={ closeRef } className='hidden' />
        </form>

      </DialogContent>
    </Dialog>
  )
}