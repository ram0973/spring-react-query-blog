import axios from 'redaxios';
import { env } from './env';

export const api = axios.create({
  baseURL: env.VITE_API_URL,
  headers: {
    "Content-type": "application/json"
  },
});
