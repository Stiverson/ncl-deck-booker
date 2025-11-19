<script setup>
import { onMounted } from 'vue'
import { useCabinStore } from '../stores/cabinStore'

const store = useCabinStore()

onMounted(() => {
  store.fetchCabins()
})

const getStatusColor = (status) => {
  switch (status) {
    case 'LIVRE': return 'bg-green-500 hover:bg-green-600 cursor-pointer'
    case 'OCUPADA': return 'bg-red-500 cursor-not-allowed'
    default: return 'bg-gray-300'
  }
}
</script>

<template>
  <div class="p-8">
    <h1 class="text-2xl font-bold mb-6 text-center text-blue-800">
      🛳️ NCL Deck Booker (Vue 3 + Scala Akka)
    </h1>

    <div v-if="store.loading" class="text-center">Carregando Deck...</div>
    
    <div v-else class="grid grid-cols-4 gap-4 max-w-md mx-auto">
      <div 
        v-for="(status, id) in store.cabins" 
        :key="id"
        @click="status === 'LIVRE' ? store.bookCabin(String(id)) : null"
        :class="['p-6 rounded-lg text-white font-bold text-center transition shadow-md', getStatusColor(status)]"
      >
        {{ id }}
        <div class="text-xs font-normal mt-1">{{ status }}</div>
      </div>
    </div>
  </div>
</template>

<style scoped>

.grid { display: grid; }
.grid-cols-4 { grid-template-columns: repeat(4, 1fr); }
.gap-4 { gap: 1rem; }
.p-8 { padding: 2rem; }
.p-6 { padding: 1.5rem; }
.mb-6 { margin-bottom: 1.5rem; }
.text-center { text-align: center; }
.font-bold { font-weight: bold; }
.text-white { color: white; }
.rounded-lg { border-radius: 0.5rem; }
.shadow-md { box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1); }
.cursor-pointer { cursor: pointer; }
.cursor-not-allowed { cursor: not-allowed; }
.bg-green-500 { background-color: #10b981; }
.bg-green-600:hover { background-color: #059669; }
.bg-red-500 { background-color: #ef4444; }
.text-blue-800 { color: #1e40af; }
.max-w-md { max-width: 28rem; }
.mx-auto { margin-left: auto; margin-right: auto; }
</style>