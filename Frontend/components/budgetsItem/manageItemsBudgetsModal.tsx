'use client'

import { useEffect, useMemo, useState } from 'react'
import { toast } from 'sonner'
import { Trash2, Plus, ArrowLeft } from 'lucide-react'

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog'
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
 } from '@/components/ui/select'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Checkbox } from '@/components/ui/checkbox'
import { Label } from '@/components/ui/label'

import { Service } from '@/types/service'
import { Product } from '@/types/product'

import { formatValue, formatText } from '@/utils/formatters'
import { addBudgetItem, getBudgetItems, deleteBudgetItem } from '@/utils/budgetItemData'

interface ManageItemsBudgetsModalProps {
  products: Product[]
  services: Service[]
  budgetId: number
  open: boolean
  onOpenChange: ( open: boolean ) => void
  onSuccess: () => void
}

const subProductOptions = [
  {
    name: 'EMBALAGEM',
    label: 'Embalagem Especial / Presente',
    value: 25,
  },
  {
    name: 'SEGURO',
    label: 'Seguro de Envio / Carga',
    value: 45,
  },
  {
    name: 'CUSTOMIZACAO',
    label: 'Customização / Ajuste de Fábrica',
    value: 75,
  },
]

const subServiceOptions = [
  {
    name: 'GARANTIA',
    label: 'Garantia Estendida',
    value: 120,
  },
  {
    name: 'NOTURNO',
    label: 'Adicional Noturno',
    value: 150,
  },
  {
    name: 'URGENTE',
    label: 'Taxa de urgência',
    value: 170,
  },
]

export function ManageItemsBudgetsModal( {
  products,
  services,
  budgetId,
  open,
  onOpenChange,
  onSuccess,
}: ManageItemsBudgetsModalProps ) {
  const [ view, setView ] = useState<'list' | 'add'>('list')
  
  const [ itemsList, setItemsList ] = useState<any[]>([])
  const [ isLoadingData, setIsLoadingData ] = useState(true)

  const [ isLoadingSubmit, setIsLoadingSubmit ] = useState(false)
  const [ itemType, setItemType ] = useState<'PRODUTO' | 'SERVICO' | ''>('')
  const [ selectedItemId, setSelectedItemId ] = useState<string>('')
  const [ quantity, setQuantity ] = useState<number>(1)

  const [ selectedSubServices, setSelectedSubServices ] = useState< string[] >([])
  const [ selectedSubProducts, setSelectedSubProducts ] = useState< string[] >([])

  useEffect(() => {
    if ( open ) {
      setView('list')
      fetchItems()
    }
  }, [ open, budgetId ])

  async function fetchItems() {
    setIsLoadingData(true)
    const data = await getBudgetItems( budgetId )
    setItemsList(data)
    setIsLoadingData(false)
  }

  async function handleDelete( itemId: number ) {
    const res = await deleteBudgetItem(budgetId, itemId)
    if ( res.success ) {
      toast.success("Item removido!")
      fetchItems()
      onSuccess()
    } else {
      toast.error("Erro ao deletar item.")
    }
  }

  const itemSelected = useMemo(() => {
    if ( !selectedItemId || !itemType ) return null;
    if ( itemType === 'PRODUTO' ) return products.find(p => p.id.toString() === selectedItemId )
    if ( itemType === 'SERVICO' ) return services.find(s => s.id.toString() === selectedItemId)
    return null;
  }, [ selectedItemId, itemType, products, services ] )

  const subServiceValue = subServiceOptions
    .filter(option => selectedSubServices.includes(option.name))
    .reduce(( sum, option ) => sum + option.value, 0)

    const subProductValue = subProductOptions
    .filter(option => selectedSubProducts.includes( option.name ) )
    .reduce(( sum, option ) => sum + option.value, 0)

  const itemPrice = itemSelected?.valorUnitario || 0

  const total = ( itemPrice + subServiceValue + subProductValue ) * quantity

  async function handleAddItem( e: React.FormEvent<HTMLFormElement> ) {
    e.preventDefault()
    if ( !selectedItemId || !itemType ) {
      toast.error('Por favor, selecione um tipo e um item.')
      return
    }

    if ( itemType === 'PRODUTO' && itemSelected ) {
      const product = itemSelected as Product

      if ( product.estoque === 0 ) {
        toast.error('Este produto está fora de estoque no momento!');
        return;
      }
    }

    setIsLoadingSubmit( true )
    const formData = new FormData()

    formData.append( 'orcamentoId', String( budgetId ) )
    formData.append( 'tipoOrcamentoItem', itemType )
    formData.append( 'quantidade', String( quantity ) )
    formData.append( 'valorUnitario', String( itemPrice ) )
    formData.append( 'subtotal', String( total ) )

    if ( itemSelected?.nome ) formData.append('descricao', itemSelected.nome)

    if ( itemType === 'PRODUTO' ) formData.append('produto_id', selectedItemId )
    else if ( itemType === 'SERVICO' ) formData.append('servico_id', selectedItemId )

    try {
      const result = await addBudgetItem( formData, budgetId, selectedSubServices )

     if ( result.success ) {
        toast.success( 'Item adicionado ao orçamento com sucesso!' )
        
        setItemType('')
        setSelectedItemId('')
        setQuantity(1)
        setView('list')
        fetchItems()
        onSuccess()
      } else {
        toast.error('Falha ao adicionar o item.')
      }
    } catch ( err ) {
      toast.error('Erro no servidor.')
    } finally {
      setIsLoadingSubmit(false)
    }
  }

  function handleDecoratorServiceToggle( id: string, checked: boolean ) {
    if ( checked ) {
      setSelectedSubServices( prev => [ ...prev, id ] )
    } else {
      setSelectedSubServices( prev => prev.filter(item => item !== id ) )
    }
  }

  function handleDecoratorProductToggle( id: string, checked: boolean ) {
    if ( checked ) {
      setSelectedSubProducts( prev => [ ...prev, id ] )
    } else {
      setSelectedSubProducts( prev => prev.filter(item => item !== id ) )
    }
  }

  return (
    <Dialog open={ open } onOpenChange={ onOpenChange }>
      <DialogContent className='sm:max-w-2xl min-h-100 flex flex-col'>

        <DialogHeader className="border-b pb-4">
          <DialogTitle className="flex justify-between items-center">
            <span className="text-xl">
              Gerenciar Itens - Orçamento #{ budgetId }
            </span>
            { view === 'list' ? (
              <Button size="sm" onClick={() => setView('add')} className="gap-2">
                <Plus size={ 16 } /> Novo Item
              </Button>
            ) : (
              <Button size="sm" variant="outline" onClick={() => setView('list')} className="gap-2">
                <ArrowLeft size={ 16 } /> Voltar para Lista
              </Button>
            )}
          </DialogTitle>
        </DialogHeader>

        { view === 'list' && (
          <div className="flex-1 overflow-y-auto py-2">
            { isLoadingData ? (
              <div className="flex justify-center py-10">
                <p> Carregando itens...</p>
              </div>
            ) : (
              <div className="border rounded-md overflow-hidden">
                <table className="w-full text-sm text-left">
                  <thead className="bg-muted/50 text-muted-foreground">
                    <tr>
                      <th className="p-3 font-semibold">Descrição</th>
                      <th className="p-3 font-semibold">Tipo</th>
                      <th className="p-3 font-semibold text-center">Qtd</th>
                      <th className="p-3 font-semibold text-right">Subtotal</th>
                      <th className="p-3 font-semibold text-center">Ações</th>
                    </tr>
                  </thead>
                  <tbody>
                    { itemsList.map(item => (
                      <tr key={ item.id } className="border-b last:border-0 hover:bg-muted/20">
                        <td className="p-3">{ item.descricao }</td>
                        <td className="p-3">
                           <span className={ `px-2 py-1 text-xs font-semibold rounded-full ${ item.tipoOrcamentoItem === 'PRODUTO' ? 'bg-blue-700 text-blue-100' : 'bg-purple-700 text-purple-100' }`}>
                             { formatText(item.tipoOrcamentoItem) }
                           </span>
                        </td>
                        <td className="p-3 text-center">{ item.quantidade }</td>
                        <td className="p-3 text-right font-medium">{ formatValue( item.subtotal ) }</td>
                        <td className="p-3 text-center">
                          <Button variant="ghost" size="icon" onClick={() => handleDelete(item.id)} title="Remover item">
                            <Trash2 size={ 18 } className="text-red-500" />
                          </Button>
                        </td>
                      </tr>
                    ))}
                    { itemsList.length === 0 && (
                      <tr>
                        <td colSpan={5} className="p-8 text-center text-muted-foreground">
                          Nenhum item adicionado neste orçamento ainda.<br/>
                          Clique em "Novo Item" para adicionar.
                        </td>
                      </tr>
                    )}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        )}

        { view === 'add' && (
          <form onSubmit={ handleAddItem } className='flex flex-col gap-4 py-4 animate-in fade-in slide-in-from-bottom-4'>
            <div className='flex justify-between gap-4 px-2'>
              <div className='flex flex-col gap-2 w-1/3'>
                <label className='text-sm font-semibold'>Tipo de item</label>
                <Select
                  value={ itemType }
                  onValueChange={ ( val: 'PRODUTO' | 'SERVICO' ) => {
                    setItemType(val)
                    setSelectedItemId('')
                    setQuantity(1)
                    setSelectedSubServices([])
                  } }
                >
                  <SelectTrigger><SelectValue placeholder='Tipo' /></SelectTrigger>
                  <SelectContent>
                    <SelectItem value='PRODUTO'>Produto</SelectItem>
                    <SelectItem value='SERVICO'>Serviço</SelectItem>
                  </SelectContent>
                </Select>
              </div>

              <div className='flex flex-col gap-2 w-2/3'>
                <label className='text-sm font-semibold'>Selecionar { itemType === 'PRODUTO' ? 'Produto' : itemType === 'SERVICO' ? 'Serviço' : 'Item' }</label>
                <Select key={ itemType } value={ selectedItemId } onValueChange={ setSelectedItemId } disabled={ !itemType }>
                  <SelectTrigger><SelectValue placeholder={ itemType ? 'Selecione...' : 'Escolha o tipo antes' } /></SelectTrigger>
                  <SelectContent>
                    <SelectGroup>
                      { itemType === 'PRODUTO' && products?.map( p => <SelectItem key={ `prod-${ p.id }` } value={ p.id.toString() }>{ p.nome }</SelectItem> )}
                      { itemType === 'SERVICO' && services?.map( s => <SelectItem key={ `serv-${ s.id }` } value={ s.id.toString() }>{ s.nome }</SelectItem> )}
                    </SelectGroup>
                  </SelectContent>
                </Select>
              </div>

              { itemType === 'SERVICO' && selectedItemId && (
                <div className='flex flex-col gap-3 w-2/3'>
                  <label className='text-sm font-semibold'>
                    MicroServiços Adicionais:
                  </label>
                  <div className='flex flex-col gap-4'>
                    { subServiceOptions.map( ( option ) => (
                      <div key={ option.name } className='flex items-center space-x-2'>
                        <Checkbox
                          id={ option.name }
                          checked={ selectedSubServices.includes(option.name ) }
                          onCheckedChange={ ( checked ) => handleDecoratorServiceToggle( option.name, checked as boolean ) }
                        />
                        <div className='flex flex-col gap-2' >
                          <Label
                            htmlFor={ option.name }
                            className='text-xs font-medium leading-none cursor-pointer'
                          >
                            { option.label }
                          </Label>
                          <p className='text-xs font-medium leading-none cursor-pointer' >
                            (+ { formatValue(option.value) })
                          </p>
                        </div>
                      </div>
                    ) ) }
                  </div>
                </div>
              ) }

              { itemType === 'PRODUTO' && selectedItemId && (
                <div className='flex flex-col gap-3 w-2/3'>
                  <label className='text-sm font-semibold'>
                    MicroProdutos Adicionais:
                  </label>
                  <div className='flex flex-col gap-4'>
                    { subProductOptions.map( ( option ) => (
                      <div key={ option.name } className='flex items-center space-x-2'>
                        <Checkbox
                          id={ option.name }
                          checked={ selectedSubProducts.includes(option.name) }
                          onCheckedChange={ ( checked ) => handleDecoratorProductToggle( option.name, checked as boolean ) }
                        />
                        <div className='flex flex-col gap-2' >
                          <Label
                            htmlFor={ option.name }
                            className='text-xs font-medium leading-none cursor-pointer'
                          >
                            { option.label }
                          </Label>
                          <p className='text-xs font-medium leading-none cursor-pointer' >
                            (+ { formatValue(option.value) })
                          </p>
                        </div>
                      </div>
                    ) ) }
                  </div>
                </div>
              ) }

            </div>

            <div className='flex gap-4 items-center justify-between mt-4 px-2' >
              <div className='flex flex-col gap-2 w-1/3'>
                <label className='text-sm font-semibold'>Quantidade</label>
                <Input type='number' min='1' value={ itemType === 'SERVICO' ? 1 : quantity } onChange={ ( e ) => setQuantity(Number( e.target.value ) ) } required disabled={ !selectedItemId || itemType === 'SERVICO' } />
              </div>
              <div className='flex flex-col gap-2 w-1/3'>
                <label className='text-sm font-semibold whitespace-nowrap'>Preço do { itemType ? formatText( itemType ) : 'Item' }</label>
                <p className='h-10 py-2'>{ formatValue( itemPrice ) }</p>
              </div>
            </div>

            <div className='flex gap-2 items-center mt-2 p-3 bg-muted/30 rounded-md border'>
                <span className='text-muted-foreground text-sm font-semibold'>Subtotal:</span>
                <span className='font-bold text-lg text-blue-600 ml-auto'>{ formatValue( total ) }</span>
            </div>

            <Button type='submit' className='mt-4 w-full' disabled={ isLoadingSubmit || !selectedItemId }>
              { isLoadingSubmit ? 'Adicionando...' : 'Adicionar ao Orçamento' }
            </Button>
          </form>
        )}

      </DialogContent>
    </Dialog>
  )
}