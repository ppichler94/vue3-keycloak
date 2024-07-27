<template>
  <button class="button__logout" @click="handleLogout">Log Out</button>
</template>

<script setup  lang="ts">

import { useUserManager } from '@/stores/usermanager'

const usermanager = useUserManager().usermanager

const handleLogout = () => usermanager.signoutPopup()
</script>
