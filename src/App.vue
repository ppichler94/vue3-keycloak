<script setup lang="ts">
import { RouterView } from 'vue-router'
import { useUserManager } from '@/stores/usermanager'
import { storeToRefs } from 'pinia'

const { authenticated, roles } = storeToRefs(useUserManager())
const { usermanager } = useUserManager()

</script>

<template>
  <el-container class="common-layout">
  <el-header>
    <el-menu mode="horizontal" :router="true" :default-active="$route.path">
      <el-menu-item index="/products" :disabled="!authenticated">Products</el-menu-item>
      <el-menu-item index="/orders" :disabled="!authenticated">Orders</el-menu-item>
      <el-menu-item index="/cart" :disabled="!authenticated">Cart</el-menu-item>
      <el-sub-menu index="management" :disabled="!roles.includes('admin')">
        <template #title>Management</template>
        <el-menu-item index="2-1">item one</el-menu-item>
        <el-menu-item index="2-2">item two</el-menu-item>
        <el-menu-item index="2-3">item three</el-menu-item>
      </el-sub-menu>
      <el-menu-item v-if="!authenticated" @click="usermanager.signinPopup()">Login</el-menu-item>
      <el-menu-item v-if="authenticated" @click="usermanager.signoutPopup()">Logout</el-menu-item>
    </el-menu>
  </el-header>

  <el-main>
    <RouterView />
  </el-main>
  </el-container>
</template>

<style scoped>
</style>
