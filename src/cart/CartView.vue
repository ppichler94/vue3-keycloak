<script setup lang="ts">

import { type CartItem, useCart } from '@/cart/useCart'
import { ref } from 'vue'
import router from '@/router'

const {getCart} = useCart()

const cart = ref<CartItem[]>([])

getCart().then((c) => cart.value = c)

function update() {

}

async function checkout() {
  void router.push({ name: 'checkout'})
}
</script>

<template>
  <el-table :data="cart" stripe>
    <el-table-column prop="productName" label="Product" />
    <el-table-column prop="productPrice" label="Price" />
    <el-table-column prop="amount" label="Amount" />
  </el-table>

  <div class="pt-4">
    <el-button @click="update()" type="primary">Update Cart</el-button>
    <el-button @click="checkout()" type="success">Checkout</el-button>
  </div>

</template>

<style scoped>

</style>