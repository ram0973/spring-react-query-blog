import { DataTable } from "@/components/data-table/data-table";
import { api } from "@/lib/api";
import { queryOptions, useQuery } from "@tanstack/react-query";
import { createFileRoute } from "@tanstack/react-router";
import { columns } from "./-columns";
import type { User } from "@/types/User";

const getUsers = async (): Promise<User[]> => (await api.get('/users-all')).data.users

const usersQueryOptions = () =>
	queryOptions({
		queryKey: ['users'],
		queryFn: getUsers,
	})


export const Route = createFileRoute("/dashboard/users/")({
  component: RouteComponent,
  loader: async ({ context }) => {
		await context.queryClient.ensureQueryData(usersQueryOptions())
	},
	head: () => ({
		meta: [{ title: 'Dashboard | Users' }],
	}),

});

function RouteComponent() {
  const { data: users } = useQuery(usersQueryOptions())
	return (
		<DataTable data={users ?? []} columns={columns} findByField="email" />
	)

}
