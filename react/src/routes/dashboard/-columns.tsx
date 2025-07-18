import type { ColumnDef } from "@tanstack/react-table";
import { format } from "date-fns";

import { AlertDialog, AlertDialogTrigger } from "@/components/ui/alert-dialog";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Link } from "@tanstack/react-router";
import { ArrowDown, ArrowUp, ArrowUpDown, MoreHorizontal } from "lucide-react";
import { DeleteUserDialog } from "./-DeleteUserDialog";
import type { User } from "@/types/User";
import { Badge } from "@/components/ui/badge";

export const columns: ColumnDef<User>[] = [
  {
    id: "select",
    header: ({ table }) => (
      <Checkbox
        checked={
          table.getIsAllPageRowsSelected() ||
          (table.getIsSomePageRowsSelected() && "indeterminate")
        }
        onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
        aria-label="Select all"
      />
    ),
    cell: ({ row }) => (
      <Checkbox
        checked={row.getIsSelected()}
        onCheckedChange={(value) => row.toggleSelected(!!value)}
        aria-label="Select row"
      />
    ),
    enableSorting: false,
    enableHiding: false,
  },
  {
    accessorKey: "firstName",
    meta: "First Name",
    header: ({ column }) => {
      const isSorted = column.getIsSorted();
      return (
        <div className="">
          <Button
            variant="ghost"
            onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
          >
            First Name
            {!isSorted && <ArrowUpDown className="h-4 w-4" />}
            {isSorted === "asc" && <ArrowUp className="h-4 w-4" />}
            {isSorted === "desc" && <ArrowDown className="h-4 w-4" />}
          </Button>
        </div>
      );
    },
    cell: ({ row }) => {
      return <div className="font-medium">{row.getValue("firstName")}</div>;
    },
  },
  {
    accessorKey: "lastName",
    meta: "Last Name",
    header: ({ column }) => {
      const isSorted = column.getIsSorted();
      return (
        <div className="">
          <Button
            variant="ghost"
            onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
          >
            Last Name
            {!isSorted && <ArrowUpDown className="h-4 w-4" />}
            {isSorted === "asc" && <ArrowUp className="h-4 w-4" />}
            {isSorted === "desc" && <ArrowDown className="h-4 w-4" />}
          </Button>
        </div>
      );
    },
    cell: ({ row }) => {
      return <div className="font-medium">{row.getValue("lastName")}</div>;
    },
  },
  {
    accessorKey: "email",
    meta: "Email",
    header: ({ column }) => {
      const isSorted = column.getIsSorted();
      return (
        <div className="">
          <Button
            variant="ghost"
            onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
          >
            Email
            {!isSorted && <ArrowUpDown className="h-4 w-4" />}
            {isSorted === "asc" && <ArrowUp className="h-4 w-4" />}
            {isSorted === "desc" && <ArrowDown className="h-4 w-4" />}
          </Button>
        </div>
      );
    },
    cell: ({ row }) => {
      return <div className="font-medium">{row.getValue("email")}</div>;
    },
  },
  {
    accessorKey: "enabled",
    meta: "Enabled",
    header: ({ column }) => {
      const isSorted = column.getIsSorted();
      return (
        <div className="">
          <Button
            variant="ghost"
            onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
          >
            Enabled
            {!isSorted && <ArrowUpDown className="h-4 w-4" />}
            {isSorted === "asc" && <ArrowUp className="h-4 w-4" />}
            {isSorted === "desc" && <ArrowDown className="h-4 w-4" />}
          </Button>
        </div>
      );
    },
    cell: ({ row }) => {
      return (
        <div className="font-medium">
          {row.getValue("enabled") ? (
            <Badge variant="secondary">Yes</Badge>
          ) : (
            <Badge variant="destructive">No</Badge>
          )}
        </div>
      );
    },
  },
  {
    accessorKey: "createdDate",
    meta: "Created",
    header: ({ column }) => {
      const isSorted = column.getIsSorted();
      return (
        <div className="">
          <Button
            variant="ghost"
            onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
          >
            Created
            {!isSorted && <ArrowUpDown className="h-4 w-4" />}
            {isSorted === "asc" && <ArrowUp className="h-4 w-4" />}
            {isSorted === "desc" && <ArrowDown className="h-4 w-4" />}
          </Button>
        </div>
      );
    },
    cell: ({ row }) => {
      const createdDate = format(
        new Date(row.getValue("createdDate")),
        "dd.MM.yyyy",
      );
      return <div className="font-medium">{createdDate}</div>;
    },
  },
  {
    id: "actions",
    cell: ({ row }) => {
      const userId = row.original.id;
      return (
        <AlertDialog>
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="ghost" className="h-8 w-8 p-0">
                <span className="sr-only">Open menu</span>
                <MoreHorizontal className="h-4 w-4" />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
              <DropdownMenuLabel>Actions</DropdownMenuLabel>
              <DropdownMenuItem>
                <Link
                  to={`/dashboard/users/update/$userId`}
                  params={{ userId: userId }}
                >
                  Update user
                </Link>
              </DropdownMenuItem>
              <DropdownMenuSeparator />
              <AlertDialogTrigger className="w-full">
                <DropdownMenuItem variant="destructive">
                  Delete User
                </DropdownMenuItem>
              </AlertDialogTrigger>
            </DropdownMenuContent>
          </DropdownMenu>
          <DeleteUserDialog id={userId} />
        </AlertDialog>
      );
    },
  },
];
