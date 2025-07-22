import z from 'zod'

export const PostCreate = z.object({
  image: z.string(),
  slug: z
    .string()
    .trim()
    .max(254, 'Slug must be less than 255 characters.')
    .regex(/^[a-z0-9-]+$/, 'Slug may contain only lowercase letters, numbers and dashes'),
  title: z
    .string()
    .trim()
    .min(2, 'Title must be at least 2 characters.')
    .max(254, 'Title must be less than 254 characters.'),
  excerpt: z.string(),
  content: z.string(),
	contentJson: z.string(),
  enabled: z.boolean()
})
