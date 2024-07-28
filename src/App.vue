<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import LoginButton from './components/buttons/LoginButton.vue'
import LogoutButton from './components/buttons/LogoutButton.vue'
import { useUserManager } from '@/stores/usermanager'
import { storeToRefs } from 'pinia'

const { authenticated } = storeToRefs(useUserManager())
</script>

<template>
  <header class="flex">
    <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="25" height="25" />

    <span class="title">Demo App</span>
    <nav class="container bg-gray-300 space-x-2">
      <RouterLink to="/products">Products</RouterLink>
      <RouterLink to="/orders">Orders</RouterLink>
      <RouterLink to="/cart">Cart</RouterLink>

      <template v-if="!authenticated">
        <LoginButton />
      </template>
      <template v-if="authenticated">
        <LogoutButton />
      </template>
    </nav>
  </header>

  <RouterView />
</template>

<style scoped>
</style>
