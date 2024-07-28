import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import SigninCallback from '@/views/SigninCallback.vue'
import SignoutCallback from '@/views/SignoutCallback.vue'
import ProductsView from '@/products/ProductsView.vue'
import OrdersView from '@/orders/OrdersView.vue'
import CartView from '@/cart/CartView.vue'
import { useUserManager } from '@/stores/usermanager'
import CheckoutView from '@/cart/CheckoutView.vue'
import NewProductView from '@/products/NewProductView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/signin-callback',
      name: 'signin-callback',
      component: SigninCallback
    },
    {
      path: '/signout-callback',
      name: 'signout-callback',
      component: SignoutCallback
    },
    {
      path: '/products',
      name: 'products',
      component: ProductsView
    },
    {
      path: '/orders',
      name: 'orders',
      component: OrdersView
    },
    {
      path: '/cart',
      name: 'cart',
      component: CartView
    },
    {
      path: '/checkout',
      name: 'checkout',
      component: CheckoutView,
    },
    {
      path: '/new-product',
      name: 'new-product',
      component: NewProductView
    }
  ]
})

router.beforeEach(async (to, from) => {
  const {authenticated} = useUserManager()
  const anyAllowed = [
    'home',
    'signin-callback',
    'signout-callback',
  ]
  if (!authenticated && !anyAllowed.includes(to.name as string)) {
    return { name: 'home'}
  }
})

export default router
