<script setup lang="ts">
import { type CartItem, useCart } from '@/cart/useCart'
import { ref } from 'vue'
import router from '@/router'

const {getCart} = useCart()

const cart = ref<CartItem[]>([])

getCart().then((c) => cart.value = c)

function abort() {
  void router.push({ name: 'cart'})
}

function confirm() {
  void router.push({ name: 'products'})
}
</script>

<template>
  <el-table :data="cart" stripe>
    <el-table-column prop="productName" label="Product" />
    <el-table-column prop="productPrice" label="Price" />
    <el-table-column prop="amount" label="Amount" />
  </el-table>

  <div class="pt-4">
    <span>Total cost: {{cart.reduce((acc, item) => acc + item.productPrice * item.amount, 0)}}</span>
  </div>

  <div class="pt-4">
    <el-button @click="abort()" type="danger">Abort</el-button>
    <el-button @click="confirm()" type="success">Place Order</el-button>
  </div>
</template>

<style scoped>

</style>