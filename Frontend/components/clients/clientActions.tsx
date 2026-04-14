'use client'

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { toast } from 'sonner';

import { Button } from '@/components/ui/button';
import { ConfirmationModal } from '@/components/confirmationModal';
import { EditClientModal } from '@/components/clients/editClientModal';

import { Client } from '@/types/client'; 
import { deleteClient } from '@/utils/clientsData';

interface ClientActionsProps {
  client: Client;
}

export function ClientActions( { client }: ClientActionsProps ) {
  const [ isDeleting, setIsDeleting ] = useState(false);
  const [ openConfirmModal, setOpenConfirmModal ] = useState(false)
  const [ openEditModal, setOpenEditModal ] = useState(false)

  const router = useRouter();

  async function handleDelete() {
    try {
      setIsDeleting(true);
      const result = await deleteClient( client )

      if ( !result.success ) {
        toast.error( result.error )
        setIsDeleting( false )
        return
      }

      toast.success( `${ client.nome } excluído com sucesso!`)
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
          title={ `Excluir cliente "${ client.nome }"?` }
          description='Tem certeza que deseja excluir este cliente? Esta ação não poderá ser desfeita.'
          onConfirm={ handleDelete }
          confirmText='Excluir Cliente'
          cancelText='Cancelar'
          loading={ isDeleting }
      />
      <EditClientModal
        open={ openEditModal }
        onOpenChange={ setOpenEditModal }
        client={ client }
        onSuccess={ () => router.refresh() }
      />
    </>
  );
}