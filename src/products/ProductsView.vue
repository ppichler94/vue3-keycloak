<script setup lang="ts">
import { type Product, useProducts } from '@/products/useProducts'
import { ref } from 'vue'
import { useCart } from '@/cart/useCart'

const {getProducts} = useProducts()
const {add} = useCart()

let tableData = ref<Product[]>([])
getProducts().then((p) => tableData.value = p.map((v) => ({
  name: v.name,
  price: v.price / 100,
  id: v.id,
  description: v.description,
})))

async function addToCart(index: number) {
  const id = tableData.value[index].id
  console.log(`add Product ${id} to cart`)
  add(id)
}
</script>

<template>
    <h2 v-if="tableData.length === 0">No products yet</h2>
      <el-table v-else :data="tableData" stripe>
        <el-table-column prop="name" label="Name" />
        <el-table-column prop="price" label="Name" />
        <el-table-column label="" >
          <template #default="scope">
            <button @click="addToCart(scope.$index)">Add to cart</button>
          </template>
        </el-table-column>
      </el-table>
</template>

<style scoped>

</style>