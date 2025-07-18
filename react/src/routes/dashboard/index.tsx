import { createFileRoute } from "@tanstack/react-router";

const usersQueryOptions = () =>
	queryOptions({
		queryKey: ['users'],
		queryFn: getUsers,
	})


export const Route = createFileRoute("/dashboard/")({
  component: RouteComponent,
  loader: async ({ context }) => {
		await context.queryClient.ensureQueryData(usersQueryOptions())
	},
	head: () => ({
		meta: [{ title: 'Dashboard | Users' }],
	}),

});

function RouteComponent() {
  const { data: users } = useSuspenseQuery(usersQueryOptions())

	return (
		<DataTable data={users ?? []} columns={columns} findByField="name" />
	)

}
