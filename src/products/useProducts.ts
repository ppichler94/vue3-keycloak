import axios from 'axios'

export function useProducts() {
  const backendUrl = import.meta.env.VITE_BACKEND_URL

  async function getProducts(): Promise<Product[]> {
    const result = await axios.get(`${backendUrl}/products`)
    return result.data
  }

  async function addProduct(product: NewProduct) {
    await axios.post(`${backendUrl}/products`, product)
  }

  return {
    getProducts,
    addProduct
  }
}

export type Product = {
  id: number,
  name: string,
  description: string,
  price: number,
}

export type NewProduct = {
  name: string,
  description: string,
  price: number,
}
