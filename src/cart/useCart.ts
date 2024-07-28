import axios from 'axios'

export function useCart() {
  const backendUrl = import.meta.env.VITE_BACKEND_URL

  async function add(id: number) {
    await axios.post(`${backendUrl}/cart/add/${id}`)
  }

  return {
    add
  }
}