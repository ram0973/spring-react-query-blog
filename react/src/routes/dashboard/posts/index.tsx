import { DataTable } from "@/components/data-table/data-table";
import { api } from "@/lib/api";
import { queryOptions, useQuery } from "@tanstack/react-query";
import { createFileRoute } from "@tanstack/react-router";
import { columns } from "./-columns";
import type { Post } from "@/types/Post";

const getPosts = async (): Promise<Post[]> => (await api.get('/posts-all')).data.posts

const postsQueryOptions = () =>
	queryOptions({
		queryKey: ['posts'],
		queryFn: getPosts,
	})


export const Route = createFileRoute("/dashboard/posts/")({
  component: RouteComponent,
  loader: async ({ context }) => {
		await context.queryClient.ensureQueryData(postsQueryOptions())
	},
	head: () => ({
		meta: [{ title: 'Dashboard | Posts' }],
	}),

});

function RouteComponent() {
  const { data: posts } = useQuery(postsQueryOptions())
	return (
		<DataTable data={posts ?? []} columns={columns} findByField="title" />
	)

}
