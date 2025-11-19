<script setup>
import CabinGrid from './components/CabinGrid.vue'
import LoginScreen from './components/LoginScreen.vue'
import { useCabinStore } from './stores/cabinStore'

const store = useCabinStore()
</script>

<template>
  <LoginScreen v-if="!store.currentUser" />
  <CabinGrid v-else />
</template>