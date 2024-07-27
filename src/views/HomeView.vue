<script setup lang="ts">
import TheWelcome from '../components/TheWelcome.vue'
import { useUserManager } from '@/stores/usermanager'
import { storeToRefs } from 'pinia'

const { user } = storeToRefs(useUserManager())
</script>

<template>
  <main>
    <TheWelcome />
    <div>
      Username: {{user?.profile.preferred_username}}
    </div>
  </main>
</template>
