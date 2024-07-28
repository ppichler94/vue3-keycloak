<script setup lang="ts">

import { reactive } from 'vue'
import { useProducts } from '@/products/useProducts'
import router from '@/router'

const {addProduct} = useProducts()

const product = reactive({
  name: '',
  description: '',
  price: 0,
})

const onSubmit = async () => {
  await addProduct(product)
  void router.push({name: 'products'})
}
</script>

<template>
<el-form :model="product" label-width="auto">
  <el-form-item label="Name">
    <el-input v-model="product.name" />
  </el-form-item>
  <el-form-item label="Description">
    <el-input v-model="product.description" />
  </el-form-item>
  <el-form-item label="Price">
    <el-input v-model="product.price" />
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="onSubmit">Create</el-button>
    <el-button>Cancel</el-button>
  </el-form-item>
</el-form>
</template>

<style scoped>

</style>