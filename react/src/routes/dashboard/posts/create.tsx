import { FormInput } from '@/components/form-inputs/FormInput'
import { FormTextArea } from '@/components/form-inputs/FormTextArea'
import { Button } from '@/components/ui/button'
import { Form } from '@/components/ui/form'
import { api } from '@/lib/api'
import slugify from '@sindresorhus/slugify'
import { useMutation } from '@tanstack/react-query'
import { createFileRoute, ErrorComponent, type ErrorComponentProps, useRouter } from '@tanstack/react-router'
import { CircleAlert, LoaderCircle } from 'lucide-react'
import { useForm } from 'react-hook-form'
import { toast } from 'sonner'
import type { z } from 'zod'

export const Route = createFileRoute('/dashboard/posts/create')({
	component: CreatePostForm,
	errorComponent: PostErrorComponent,
})

function PostErrorComponent({ error }: ErrorComponentProps) {
	return <ErrorComponent error={error} />
}

function CreatePostForm() {

	const form = useForm<z.infer<typeof CreatePost>>({
		resolver: zodResolver(CreatePost),
		defaultValues: {
			slug: '',
			title: '',
			excerpt: '',
			content: '',
			contentJson: '',
		},
	})

	const createPostMutation = useMutation({
		mutationFn: async () => api.post("/posts"),
	})

	const router = useRouter()

	async function onSubmit(values: z.infer<typeof CreatePost>) {
		try {

			if (!values.slug || values.slug.trim() === '') {
				values.slug = slugify(values.title, { lowercase: true, customReplacements: [['.', '']] })
			}
			await createPostMutation.mutateAsync({ data: values })
			toast('Post has been created')
			router.invalidate()
			queryClient.invalidateQueries({
				queryKey: ['posts'],
			})
			router.navigate({ to: "/dashboard/posts" })
		} catch (error: unknown) {
			toast(`Error while creating post: ${error}`, {
				style: {
					color: 'red'
				},
				icon: <CircleAlert />
			})
		}
	}

	return (
		<>
			<title>Posts | Create post</title>
			<h1 className="mt-5 mb-5 text-3xl">Create post</h1>
			<Form {...form}>
				<form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
					<FormInput form={form} label="Title" name="title" placeholder='Enter post title' />
					<FormTextArea form={form} label="Excerpt" name="excerpt" placeholder='Enter post excerpt' />
          <FormTextArea form={form} label="Content" name="content" placeholder='Enter post content' />
					<Button type="submit" disabled={form.formState.isSubmitting}>
						{form.formState.isSubmitting && <LoaderCircle className="animate-spin" />}
						{form.formState.isSubmitting ? 'Submitting...' : 'Submit'}</Button>
				</form>
			</Form>
		</>
	)
}
