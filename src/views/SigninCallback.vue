<template>

</template>

<script setup lang="ts">
// window.close()
import { useUserManager } from '@/stores/usermanager'

const usermanager = useUserManager().usermanager

usermanager.signinCallback()
</script>

