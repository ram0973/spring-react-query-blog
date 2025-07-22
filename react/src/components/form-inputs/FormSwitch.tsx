import type { ReactNode } from 'react'
import type { Path, UseFormReturn } from 'react-hook-form'
import { FormControl, FormField, FormItem, FormLabel, FormMessage } from '../ui/form'
import { Switch } from '../ui/switch'

export const FormSwitch = <T extends Record<string, unknown>>({
  form,
  label,
  name,
  ...props
}: {
  form: UseFormReturn<T>
  label: ReactNode
  name: Path<T>
}) => {
  return (
    <FormField
      control={form.control}
      name={name}
      render={({ field }) => {
        return (
          <FormItem>
            <FormLabel>{label}</FormLabel>
            <FormControl>
              <Switch checked={field.value} onCheckedChange={field.onChange} name={name} />
            </FormControl>
            <FormMessage />
          </FormItem>
        )
      }}
    />
  )
}
