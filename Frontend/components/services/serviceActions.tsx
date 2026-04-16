'use client'

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { toast } from 'sonner';
import { Edit, Trash } from 'lucide-react';

import { Button } from '@/components/ui/button';
import { ConfirmationModal } from '@/components/confirmationModal';
import { EditServiceModal } from '@/components/services/editServiceModal';

import { Service } from '@/types/service';
import { deleteService } from '@/utils/servicesData';

interface ServiceActionsProps {
  service: Service;
}

export function ServiceActions( { service }: ServiceActionsProps ) {
  const [ isDeleting, setIsDeleting ] = useState( false );
  const [ openConfirmModal, setOpenConfirmModal ] = useState( false )
  const [ openEditModal, setOpenEditModal ] = useState( false )

  const router = useRouter();

  async function handleDelete() {
    try {
      setIsDeleting(true);
      const result = await deleteService( service )

      if ( !result.success ) {
        toast.error( result.error )
        setIsDeleting( false )
        return
      }

      toast.success( `${ service.nome } excluído com sucesso!`)
      router.refresh()

    } catch ( err ) {
      console.error('Erro ao excluir:',  err )
      toast.error( 'Não foi possível excluir esse serviço' )
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
          title={ `Excluir ${ service.nome }?` }
          description='Tem certeza que deseja excluir este serviço? Esta ação não poderá ser desfeita.'
          onConfirm={ handleDelete }
          confirmText='Excluir Serviço'
          cancelText='Cancelar'
          loading={ isDeleting }
      />
      <EditServiceModal
        open={ openEditModal }
        onOpenChange={ setOpenEditModal }
        service={ service }
        onSuccess={ () => router.refresh() }
      />
    </>
  );
}