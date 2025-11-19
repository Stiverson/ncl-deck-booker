<script setup>
import { onMounted, ref } from 'vue'
import { useCabinStore } from '../stores/cabinStore'

const store = useCabinStore()
const notification = ref('')

onMounted(() => {
  store.fetchCabins()
})

const handleBooking = async (id) => {
  await store.bookCabin(id)
 
  if (store.cabins[id] === 'BOOKED') {
    notification.value = `🎉 Booking confirmed for cabin ${id}!`
    setTimeout(() => notification.value = '', 3000)
  }
}

const getStatusClass = (status) => {
  switch (status) {
    case 'AVAILABLE': return 'available' 
    case 'BOOKED': return 'occupied'    
    default: return ''
  }
}
</script>

<template>
  <div class="app-container">
    <div class="overlay">
      <div class="content-wrapper">
        
        <header class="header">
          <h1 class="title">⚓ NCL Premium Deck</h1>
          <p class="subtitle">Book your exclusive cabin in real-time with Scala & Akka</p>
        </header>

        <div v-if="notification" class="notification-banner">
          {{ notification }}
        </div>

        <div v-if="store.loading" class="loading">Loading deck map...</div>
        
        <div v-else class="deck-grid">
          <div 
            v-for="(status, id) in store.cabins" 
            :key="id"
            @click="status === 'AVAILABLE' ? handleBooking(String(id)) : null"
            :class="['cabin-card', getStatusClass(status)]"
          >
            <div class="cabin-icon">
              <span v-if="status === 'AVAILABLE'">🛏️</span>
              <span v-else>👤</span>
            </div>
            <div class="cabin-info">
              <span class="cabin-id">{{ id }}</span>
              <span class="cabin-status">{{ status }}</span>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<style>

body, html {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  overflow-x: hidden;
}
</style>

<style scoped>
.app-container {
  min-height: 100vh;
  width: 100vw;
  background-image: url('https://images.unsplash.com/photo-1548574505-5e239809ee19?q=80&w=2064&auto=format&fit=crop');
  background-size: cover;
  background-position: center;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.overlay {
  background: rgba(0, 30, 60, 0.85);
  width: 100%;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  padding: 2rem;
}

.content-wrapper {
  max-width: 800px;
  width: 100%;
}

.header {
  text-align: center;
  margin-bottom: 2rem;
  color: white;
}

.title {
  font-size: 2.5rem;
  font-weight: 800;
  text-transform: uppercase;
  letter-spacing: 2px;
  margin-bottom: 0.5rem;
  text-shadow: 0 2px 4px rgba(0,0,0,0.3);
}

.subtitle {
  font-size: 1.1rem;
  color: #a5f3fc;
}

.deck-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
  gap: 1.5rem;
}

.cabin-card {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  transition: all 0.3s ease;
  color: white;
}

.cabin-id {
  font-weight: bold;
  font-size: 1.2rem;
  margin-top: 0.5rem;
}

.cabin-status {
  font-size: 0.8rem;
  text-transform: uppercase;
  margin-top: 0.2rem;
  opacity: 0.8;
}

.cabin-icon {
  font-size: 2rem;
}

.available {
  border-bottom: 4px solid #10b981;
}

.available:hover {
  transform: translateY(-5px);
  background: rgba(16, 185, 129, 0.2);
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.3);
}

.occupied {
  background: rgba(239, 68, 68, 0.2);
  border-bottom: 4px solid #ef4444;
  cursor: not-allowed;
  opacity: 0.7;
}

.notification-banner {
  background-color: #10b981;
  color: white;
  padding: 1rem;
  border-radius: 8px;
  text-align: center;
  margin-bottom: 2rem;
  font-weight: bold;
  animation: slideDown 0.5s ease;
}

.loading {
  color: white;
  text-align: center;
  font-size: 1.5rem;
}

@keyframes slideDown {
  from { transform: translateY(-20px); opacity: 0; }
  to { transform: translateY(0); opacity: 1; }
}
</style>