<script setup lang="ts">
import { type Product, useProducts } from '@/products/useProducts'
import { ref } from 'vue'

const {getProducts} = useProducts()

const products = ref<Product[]>([])
getProducts().then((p) => products.value = p)

async function addToCart(id: number) {
  console.log(`add Product ${id} to cart`)
}
</script>

<template>
  <div class="container py-3">
    <main>
      <h2 v-if="products.length === 0">No products yet</h2>
        <table v-else class="table-auto">
          <thead>
          <tr>
            <th scope="col">Name</th>
            <th scope="col">Price (EUR)</th>
            <th scope="col">in stock</th>
            <th scope="col"></th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="product in products" :key="product.id">
            <td>{{product.name}}</td>
            <td>{{product.price / 100}}</td>
            <td><button @click="addToCart(product.id)">Add to cart</button></td>
          </tr>
          </tbody>
        </table>
    </main>
  </div>
</template>

<style scoped>

</style>