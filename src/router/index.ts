import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import SigninCallback from '@/views/SigninCallback.vue'
import SignoutCallback from '@/views/SignoutCallback.vue'
import ProductsView from '@/views/ProductsView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
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
    }
  ]
})

export default router
