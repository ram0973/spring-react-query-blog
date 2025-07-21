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

const deletePost = async ({ id }: { id: string }) => { api.delete(`/posts/${id}`)}

export const DeletePostDialog = ({ id }: { id: string }) => {
  const deletePostMutation = useMutation({
		mutationFn: deletePost,
	})

	const router = useRouter()
	const queryClient = useQueryClient();

  const handleDelete = async () => {
    try {
      await deletePostMutation.mutateAsync({ id })
      toast.success('Post deleted successfully')
      router.invalidate()
      queryClient.invalidateQueries({
        queryKey: ['posts'],
      })
    } catch (error) {
      toast('Error while deleting post', {
        description: error instanceof Error ? error.message : 'Unknown error',
        style: { color: 'red' },
        icon: <CircleAlert />,
      })
    }
  }

  return (
    <AlertDialogContent>
      <AlertDialogHeader>
        <AlertDialogTitle>Are you absolutely sure to delete post?</AlertDialogTitle>
        <AlertDialogDescription>This action cannot be undone.</AlertDialogDescription>
      </AlertDialogHeader>
      <AlertDialogFooter>
        <AlertDialogCancel>Cancel</AlertDialogCancel>
        <AlertDialogAction onClick={handleDelete}>Delete</AlertDialogAction>
      </AlertDialogFooter>
    </AlertDialogContent>
  )
}
