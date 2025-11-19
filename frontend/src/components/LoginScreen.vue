<script setup>
import { ref } from 'vue'
import { useCabinStore } from '../stores/cabinStore'

const store = useCabinStore()
const username = ref('')

const handleLogin = () => {
  if (username.value.trim()) {
    store.login(username.value)
  }
}
</script>

<template>
  <div class="login-container">
    <div class="login-card">
      <div class="logo">⚓</div>
      <h1 class="title">Welcome Aboard</h1>
      <p class="subtitle">NCL Deck Reservation System</p>
      
      <div class="input-group">
        <input 
          v-model="username" 
          @keyup.enter="handleLogin"
          type="text" 
          placeholder="Enter your Guest Name..." 
          class="login-input"
        />
        <button @click="handleLogin" class="login-btn">
          START BOOKING
        </button>
      </div>
      
      <p class="footer-tech">Powered by Scala Akka & Vue 3</p>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  height: 100vh;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url('https://images.unsplash.com/photo-1548574505-5e239809ee19?q=80&w=2064&auto=format&fit=crop');
  background-size: cover;
  background-position: center;
}

.login-card {
  background: rgba(10, 25, 47, 0.95);
  padding: 3rem;
  border-radius: 16px;
  border: 1px solid rgba(100, 255, 218, 0.2);
  text-align: center;
  width: 100%;
  max-width: 400px;
  box-shadow: 0 20px 50px rgba(0,0,0,0.5);
  animation: fadeIn 0.8s ease-out;
}

.logo { font-size: 4rem; margin-bottom: 1rem; }

.title { color: white; font-size: 2rem; font-weight: 800; margin-bottom: 0.5rem; }
.subtitle { color: #8892b0; margin-bottom: 2rem; }

.input-group { display: flex; flex-direction: column; gap: 1rem; }

.login-input {
  padding: 1rem;
  border-radius: 8px;
  border: 1px solid #233554;
  background: #112240;
  color: white;
  font-size: 1rem;
  text-align: center;
  outline: none;
  transition: border-color 0.3s;
}

.login-input:focus { border-color: #64ffda; }

.login-btn {
  padding: 1rem;
  background: #64ffda;
  color: #0a192f;
  font-weight: bold;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1rem;
  transition: transform 0.2s;
}

.login-btn:hover { transform: translateY(-2px); background: #5ce6c4; }

.footer-tech { margin-top: 2rem; color: #8892b0; font-size: 0.8rem; opacity: 0.6; }

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>