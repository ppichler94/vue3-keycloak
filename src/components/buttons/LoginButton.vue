<template>
  <button class="button__login" @click="handleLogin">Log In</button>
</template>

<script setup  lang="ts">
import { useUserManager } from '@/stores/usermanager'

const usermanager = useUserManager().usermanager

const handleLogin = () => {
  usermanager.signinPopup()
};
</script>
