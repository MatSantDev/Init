'use client'

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { toast } from 'sonner';

import { Button } from '@/components/ui/button';
import { ConfirmationModal } from '@/components/confirmationModal';
import { EditBudgetModal } from '@/components/budgets/editBudgetModal';
import { ManageItemsBudgetsModal } from '@/components/budgetsItem/manageItemsBudgetsModal';

import { Budget } from '@/types/budget';
import { Client } from '@/types/client';
import { Product } from '@/types/product';
import { Service } from '@/types/service'

import { deleteBudget } from '@/utils/budgetsData';

interface BudgetActionsProps {
  budget: Budget;
  clients: Client[]
  products: Product[]
  services: Service[]
}

export function BudgetActions({ budget, clients, services, products }: BudgetActionsProps ) {
  const [ isDeleting, setIsDeleting ] = useState(false);

  const [ openConfirmModal, setOpenConfirmModal ] = useState(false)
  const [ openEditModal, setOpenEditModal ] = useState(false)
  const [ openManageModal, setOpenManageModal ] = useState(false)

  const router = useRouter();

  async function handleDelete() {
    try {
      setIsDeleting(true);
      const result = await deleteBudget( budget )

      if ( !result.success ) {
        toast.error( result.error )
        setIsDeleting( false )
        return
      }

      toast.success( `Orçamento do ${ budget.cliente.nome } excluído com sucesso!`)
      router.refresh()

    } catch ( err ) {
      console.error('Erro ao excluir:',  err )
      toast.error( 'Não foi possível excluir esse produto' )
    } finally {
      setIsDeleting(false);
    }
  };

  return (
    <>
      <div className='flex items-center justify-center gap-2'>
          <Button
            size='sm'
            onClick={ () => setOpenManageModal( true ) }
          >
            Gerenciar
          </Button>

        <Button
          size='sm'
          variant='outline'
          onClick={ () => setOpenEditModal(true) }
          disabled={ isDeleting }
        >
          Editar
        </Button>

        <Button
          size='sm'
          variant='destructive'
          onClick={ () => setOpenConfirmModal(true) }
          disabled={ isDeleting }
        >
          { isDeleting ? 'Excluindo...' : 'Excluir' }
        </Button>
      </div>
      <ConfirmationModal
          open={ openConfirmModal }
          onOpenChange={ setOpenConfirmModal }
          title={ `Excluir orçamento do ${ budget.cliente.nome }?` }
          description='Tem certeza que deseja excluir este orçamento? Esta ação não poderá ser desfeita.'
          onConfirm={ handleDelete }
          confirmText='Excluir Orçamento'
          cancelText='Cancelar'
          loading={ isDeleting }
      />
      <EditBudgetModal
        open={ openEditModal }
        onOpenChange={ setOpenEditModal }
        budget={ budget }
        clients={ clients }
        onSuccess={ () => router.refresh() }
      />
      <ManageItemsBudgetsModal
        open={ openManageModal }
        onSuccess={ () => router.refresh() }
        budgetId={ budget.id }
        onOpenChange={ setOpenManageModal }
        products={ products }
        services={ services }
      />
    </>
  );
}