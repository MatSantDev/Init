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

// import { addBudgetItem } from '@/utils/budgetsItemData'

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

  const [ tipoItem, setTipoItem ] = useState<'PRODUTO' | 'SERVICO'>('PRODUTO')
  const [ selectedItemId, setSelectedItemId ] = useState<string>('')
  const [ quantidade, setQuantidade ] = useState<number>(1)

  const itemSelecionado = useMemo(() => {
    if ( !selectedItemId ) return null;

    if ( tipoItem === 'PRODUTO' ) {
      return products.find(p => p.id.toString() === selectedItemId)
    } else {
      return services.find(s => s.id.toString() === selectedItemId)
    }
  }, [ selectedItemId, tipoItem, products, services ] )

  const valorUnitario = itemSelecionado?.valorUnitario || 0
  const subtotal = valorUnitario * quantidade

  async function handleSubmit( e: React.FormEvent<HTMLFormElement> ) {
    e.preventDefault()

    if ( !selectedItemId ) {
      toast.error('Por favor, selecione um item.')
      return
    }

    setIsLoading( true )

    const novoItem = {
      orcamentoId: budgetId,
      tipoOrcamentoItem: tipoItem,
      quantidade: quantidade,
      valorUnitario: valorUnitario,
      subtotal: subtotal,
      descricao: itemSelecionado?.nome,
      produto_id: tipoItem === 'PRODUTO' ? Number(selectedItemId) : null,
      servico_id: tipoItem === 'SERVICO' ? Number(selectedItemId) : null,
    }

    try {
      // 🚨 Aqui você vai chamar a sua função de API!
      // const result = await addBudgetItem(novoItem)

      // Simulando o sucesso por enquanto:
      setTimeout(() => {
        toast.success( `${tipoItem === 'PRODUTO' ? 'Produto' : 'Serviço'} adicionado ao orçamento!` )
        onSuccess() // Atualiza a tabela do Next.js
        setIsLoading(false)
        setSelectedItemId('') // Limpa o campo
        setQuantidade(1)
      }, 1000)

    } catch ( err ) {
      console.error('Erro ao adicionar item:', err )
      toast.error('Falha ao adicionar o item.')
      setIsLoading(false)
    }
  }

  return (
    <Dialog open={ open } onOpenChange={ onOpenChange }>
      <DialogContent className="sm:max-w-md">

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
                value={ tipoItem }
                onValueChange={ ( val: 'PRODUTO' | 'SERVICO' ) => {
                  setTipoItem(val)
                  setSelectedItemId('')
                }}
              >
                <SelectTrigger>
                  <SelectValue placeholder="Tipo" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="PRODUTO">
                    Produto
                  </SelectItem>
                  <SelectItem value="SERVICO">
                    Serviço
                  </SelectItem>
                </SelectContent>
              </Select>
            </div>

            <div className='flex flex-col gap-2 w-2/3'>
              <label className='text-sm font-semibold'>
                Selecionar { tipoItem === 'PRODUTO' ? 'Produto' : 'Serviço' }
              </label>
              <Select value={ selectedItemId } onValueChange={ setSelectedItemId }>
                <SelectTrigger>
                  <SelectValue placeholder="Selecione..." />
                </SelectTrigger>
                <SelectContent>
                  <SelectGroup>
                    { tipoItem === 'PRODUTO'
                      ? products?.map( p => <SelectItem key={ p.id } value={ p.id.toString() }>{ p.nome }</SelectItem> )
                      : services?.map( s => <SelectItem key={ s.id } value={ s.id.toString() }>{ s.nome }</SelectItem> )
                    }
                  </SelectGroup>
                </SelectContent>
              </Select>
            </div>
          </div>

          <div className='flex gap-4 items-end'>
            <div className='flex flex-col gap-2 w-1/3'>
              <label className='text-sm font-semibold'>Quantidade</label>
              <Input
                type="number"
                min="1"
                value={ quantidade }
                onChange={ ( e ) => setQuantidade(Number( e.target.value ) ) }
                required
              />
            </div>

            <div className='flex flex-col gap-2 w-2/3'>
              <div className="flex justify-between items-center h-10 px-3 rounded-md border bg-muted/50 text-sm">
                <span className="text-muted-foreground">Subtotal:</span>
                <span className="font-bold text-blue-600">
                  R$ {subtotal.toFixed(2).replace('.', ',')}
                </span>
              </div>
            </div>
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