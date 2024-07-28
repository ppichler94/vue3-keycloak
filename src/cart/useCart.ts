import axios from 'axios'

export function useCart() {
  const backendUrl = import.meta.env.VITE_BACKEND_URL

  async function addToCart(id: number) {
    await axios.post(`${backendUrl}/cart/add/${id}`)
  }

  async function getCart(): Promise<CartItem[]> {
    const result = await axios.get(`${backendUrl}/cart`)
    return result.data
  }

  async function updateCart() {

  }

  async function checkoutCart() {

  }

  return {
    addToCart,
    getCart,
    updateCart,
    checkoutCart,
  }
}

export type CartItem = {
  productId: number,
  amount: number,
  productName: string,
  productPrice: number,
}