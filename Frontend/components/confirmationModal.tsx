'use client'

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogDescription,
  DialogFooter,
} from "@/components/ui/dialog"

import { Button } from "@/components/ui/button"

type ConfirmationModalProps = {
  open: boolean
  onOpenChange: (open: boolean) => void
  title?: string
  description?: string
  onConfirm: () => void
  confirmText?: string
  cancelText?: string
  loading?: boolean
}

export function ConfirmationModal({
  open,
  onOpenChange,
  title = "Tem certeza?",
  description = "Essa ação não pode ser desfeita.",
  onConfirm,
  confirmText = "Confirmar",
  cancelText = "Cancelar",
  loading = false
}: ConfirmationModalProps) {
  return (
    <Dialog open={ open } onOpenChange={ onOpenChange } >
      <DialogContent>
        <DialogHeader>
          <DialogTitle className='text-xl font-semibold' >
            { title }
          </DialogTitle>
          <DialogDescription className='text-lg' >
            { description }
          </DialogDescription>
        </DialogHeader>

        <DialogFooter className="flex gap-2">
          <Button
            variant="outline"
            onClick={ () => onOpenChange(false) }
            disabled={ loading }
          >
            { cancelText }
          </Button>

          <Button
            variant="destructive"
            onClick={ onConfirm }
            disabled={ loading }
          >
            { loading ? "Carregando..." : confirmText }
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  )
}