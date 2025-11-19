<script setup>
import { onMounted, ref } from 'vue'
import { useCabinStore } from '../stores/cabinStore'

const store = useCabinStore()
const notification = ref('')

onMounted(() => {
  store.fetchCabins()
})


const handleLogout = () => {
  store.logout()
}

const handleReset = async () => {
  if(confirm('Are you sure you want to reset the simulation? All bookings will be cleared.')) {
    const success = await store.resetSimulation()
    if (success) {
      notification.value = '🔄 System Reset Successful! Deck is clear.'
      setTimeout(() => notification.value = '', 3000)
    }
  }
}


const handleBooking = async (id) => {
  
  const result = await store.bookCabin(id)
  
  if (result.success) {
    notification.value = `🎉 Success! Cabin ${id} booked for ${store.currentUser}!`
  } else {
    notification.value = `⚠️ ${result.message}`
  }
  setTimeout(() => notification.value = '', 3000)
}

const quickBook = (category) => {
  const availableOption = Object.keys(store.cabins).find(id => 
    id.startsWith(category) && store.cabins[id] === 'AVAILABLE'
  )

  if (availableOption) {
    handleBooking(availableOption)
  } else {
    notification.value = `⚠️ Sorry! No ${category}-Class cabins available.`
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
        
        <header class="header-flex">
          <div class="user-info">
            <h1 class="title">⚓ NCL Premium Deck</h1>
            <p class="subtitle">
              Welcome, <strong style="color: #64ffda">{{ store.currentUser }}</strong>!
            </p>
          </div>
          
          <div class="controls">
            <button @click="handleReset" class="btn-reset" title="Reset Simulation">
              🔄 Reset
            </button>
            <button @click="handleLogout" class="btn-logout" title="Logout">
              🚪 Logout
            </button>
          </div>
        </header>

        <div class="smart-booking-panel">
          <h3 class="panel-title">Quick Book by Category</h3>
          <div class="buttons-row">
            <button @click="quickBook('A')" class="btn-vip">
              👑 VIP Suite <span class="price">$300</span>
            </button>
            <button @click="quickBook('B')" class="btn-std">
              🛟 Standard <span class="price">$150</span>
            </button>
            <button @click="quickBook('C')" class="btn-eco">
              ⚓ Economy <span class="price">$100</span>
            </button>
          </div>
        </div>

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
            <div class="icon-area">
              <span v-if="status === 'AVAILABLE'" class="icon-free">🛏️</span>
              <span v-else class="icon-busy">👤</span>
            </div>
            <div class="info-area">
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
body, html { margin: 0; padding: 0; width: 100%; height: 100%; overflow-x: hidden; }
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
  background: rgba(10, 25, 47, 0.9); 
  width: 100%;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  padding: 2rem;
}

.content-wrapper { max-width: 900px; width: 100%; }


.header-flex {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
  color: white;
  border-bottom: 1px solid rgba(255,255,255,0.1);
  padding-bottom: 1rem;
}

.title { font-size: 1.8rem; font-weight: 800; text-transform: uppercase; letter-spacing: 2px; margin: 0; }
.subtitle { font-size: 1rem; color: #a5f3fc; letter-spacing: 1px; margin: 0; }


.controls { display: flex; gap: 10px; }

.btn-logout {
  background: transparent;
  border: 1px solid #ef4444;
  color: #ef4444;
  padding: 0.5rem 1rem;
  min-width: auto;
  font-size: 0.8rem;
}
.btn-logout:hover { background: #ef4444; color: white; transform: translateY(-2px); }

.btn-reset {
  background: transparent;
  border: 1px solid #64ffda;
  color: #64ffda;
  padding: 0.5rem 1rem;
  min-width: auto;
  font-size: 0.8rem;
}
.btn-reset:hover { background: #64ffda; color: #0a192f; transform: translateY(-2px); }


.smart-booking-panel {
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 16px;
  padding: 1.5rem;
  margin-bottom: 2rem;
  text-align: center;
}

.panel-title { color: #ccd6f6; margin-bottom: 1rem; font-size: 0.9rem; text-transform: uppercase; }
.buttons-row { display: flex; gap: 1rem; justify-content: center; flex-wrap: wrap; }

button {
  padding: 0.8rem 1.5rem;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-weight: bold;
  transition: transform 0.2s;
  color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 120px;
}

button:hover { transform: translateY(-3px); filter: brightness(1.1); }
button:active { transform: translateY(0); }

.btn-vip { background: linear-gradient(135deg, #ffd700, #b8860b); color: #000; }
.btn-std { background: linear-gradient(135deg, #3b82f6, #1e40af); }
.btn-eco { background: linear-gradient(135deg, #10b981, #059669); }

.price { font-size: 0.8rem; opacity: 0.9; margin-top: 4px; font-weight: normal; }


.deck-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
  gap: 1rem;
}

.cabin-card {
  background: rgba(17, 34, 64, 0.8);
  border: 1px solid rgba(100, 255, 218, 0.1);
  border-radius: 8px;
  padding: 1rem;
  display: flex;
  flex-direction: column; 
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: all 0.3s ease;
  color: #8892b0;
  height: 120px;
}

.icon-area { font-size: 1.5rem; margin-bottom: 0.5rem; }
.info-area { text-align: center; }

.cabin-id { font-weight: bold; font-size: 1.1rem; color: #e6f1ff; display: block; }
.cabin-status { font-size: 0.7rem; text-transform: uppercase; letter-spacing: 1px; margin-top: 4px; display: block;}

.available { border-bottom: 3px solid #64ffda; }
.available:hover { background: rgba(100, 255, 218, 0.1); }
.available .icon-free { color: #64ffda; }

.occupied { border-bottom: 3px solid #ef4444; opacity: 0.6; cursor: not-allowed; }
.occupied .icon-busy { color: #ef4444; }
.occupied .cabin-id { color: #8892b0; }

.notification-banner {
  background-color: #64ffda;
  color: #0a192f;
  padding: 1rem;
  border-radius: 4px;
  text-align: center;
  margin-bottom: 1.5rem;
  font-weight: bold;
  box-shadow: 0 4px 12px rgba(100, 255, 218, 0.3);
}

.loading { color: #8892b0; text-align: center; margin-top: 2rem; }
</style>