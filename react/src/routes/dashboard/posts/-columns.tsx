import type { ColumnDef } from "@tanstack/react-table";
import { format } from "date-fns";

import { AlertDialog, AlertDialogTrigger } from "@/components/ui/alert-dialog";
import { Badge } from "@/components/ui/badge";
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
import type { Post } from "@/types/Post";
import { Link } from "@tanstack/react-router";
import { ArrowDown, ArrowUp, ArrowUpDown, MoreHorizontal } from "lucide-react";
import { DeletePostDialog } from "./-DeletePostDialog";

export const columns: ColumnDef<Post>[] = [
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
    accessorKey: "title",
    meta: "Title",
    header: ({ column }) => {
      const isSorted = column.getIsSorted();
      return (
        <div className="">
          <Button
            variant="ghost"
            onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
          >
            Title
            {!isSorted && <ArrowUpDown className="h-4 w-4" />}
            {isSorted === "asc" && <ArrowUp className="h-4 w-4" />}
            {isSorted === "desc" && <ArrowDown className="h-4 w-4" />}
          </Button>
        </div>
      );
    },
    cell: ({ row }) => {
      return <div className="font-medium">{row.getValue("title")}</div>;
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
      const postId = row.original.id;
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
                  to={`/dashboard/posts/update/$postId`}
                  params={{ postId: postId }}
                >
                  Update post
                </Link>
              </DropdownMenuItem>
              <DropdownMenuSeparator />
              <AlertDialogTrigger className="w-full">
                <DropdownMenuItem variant="destructive">
                  Delete Post
                </DropdownMenuItem>
              </AlertDialogTrigger>
            </DropdownMenuContent>
          </DropdownMenu>
          <DeletePostDialog id={postId} />
        </AlertDialog>
      );
    },
  },
];
