'use client'

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { toast } from 'sonner';
import { Edit, Trash } from 'lucide-react';

import { Button } from '@/components/ui/button';
import { ConfirmationModal } from '@/components/confirmationModal';
import { EditProductModal } from '@/components/products/editProductModal';

import { Product } from '@/types/product';
import { deleteProduct } from '@/utils/productsData';

interface ProductActionsProps {
  product: Product;
}

export function ProductActions({ product }: ProductActionsProps) {
  const [ isDeleting, setIsDeleting ] = useState(false);
  const [ openConfirmModal, setOpenConfirmModal ] = useState(false)
  const [ openEditModal, setOpenEditModal ] = useState(false)

  const router = useRouter();

  async function handleDelete() {
    try {
      setIsDeleting(true);
      const result = await deleteProduct( product )

      if ( !result.success ) {
        toast.error( result.error )
        setIsDeleting( false )
        return
      }

      toast.success( `${ product.nome } excluído com sucesso!`)
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
          variant='outline'
          onClick={ () => setOpenEditModal(true) }
          disabled={ isDeleting }
        >
          <Edit />
        </Button>

        <Button
          size='sm'
          variant='destructive'
          onClick={ () => setOpenConfirmModal(true) }
          disabled={ isDeleting }
        >
          <Trash />
        </Button>
      </div>
      <ConfirmationModal
          open={ openConfirmModal }
          onOpenChange={ setOpenConfirmModal }
          title={ `Excluir ${ product.nome }?` }
          description='Tem certeza que deseja excluir este produto? Esta ação não poderá ser desfeita.'
          onConfirm={ handleDelete }
          confirmText='Excluir Produto'
          cancelText='Cancelar'
          loading={ isDeleting }
      />
      <EditProductModal
        open={ openEditModal }
        onOpenChange={ setOpenEditModal }
        product={ product }
        onSuccess={ () => router.refresh() }
      />
    </>
  );
}