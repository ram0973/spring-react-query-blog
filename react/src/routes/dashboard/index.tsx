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
  return <div className="p-2">Hello Dashboard!</div>;
}
