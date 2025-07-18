import {
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from '@/components/ui/alert-dialog'
import { api } from '@/lib/api'
import { useMutation, useQueryClient } from '@tanstack/react-query'
import { useRouter } from '@tanstack/react-router'
import { CircleAlert } from 'lucide-react'
import { toast } from 'sonner'

const deleteUser = async ({ id }: { id: string }) => { api.delete(`/users/${id}`)}

export const DeleteUserDialog = ({ id }: { id: string }) => {
  const deleteUserMutation = useMutation({
		mutationFn: deleteUser,
	})

	const router = useRouter()
	const queryClient = useQueryClient();

  const handleDelete = async () => {
    try {
      await deleteUserMutation.mutateAsync({ id })
      toast.success('User deleted successfully')
      router.invalidate()
      queryClient.invalidateQueries({
        queryKey: ['users'],
      })
    } catch (error) {
      toast('Error while deleting user', {
        description: error instanceof Error ? error.message : 'Unknown error',
        style: { color: 'red' },
        icon: <CircleAlert />,
      })
    }
  }

  return (
    <AlertDialogContent>
      <AlertDialogHeader>
        <AlertDialogTitle>Are you absolutely sure to delete user?</AlertDialogTitle>
        <AlertDialogDescription>This action cannot be undone.</AlertDialogDescription>
      </AlertDialogHeader>
      <AlertDialogFooter>
        <AlertDialogCancel>Cancel</AlertDialogCancel>
        <AlertDialogAction onClick={handleDelete}>Delete</AlertDialogAction>
      </AlertDialogFooter>
    </AlertDialogContent>
  )
}
