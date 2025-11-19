import { defineStore } from 'pinia'
import axios from 'axios'

export const useCabinStore = defineStore('cabin', {
  state: () => ({
    cabins: {},
    loading: false,
    error: null,
    currentUser: null 
  }),

  actions: {
 
    login(username) {
      this.currentUser = username
    },

    logout() {
      this.currentUser = null
    },

    async resetSimulation() {
      try {
        await axios.post('/api/reset')
        await this.fetchCabins() 
        return true
      } catch (err) {
        console.error(err)
        return false
      }
    },

    async fetchCabins() {
      this.loading = true
      try {
        const response = await axios.get('/api/cabins')
        this.cabins = response.data.cabins
      } catch (err) {
        this.error = 'Erro ao carregar cabines.'
        console.error(err)
      } finally {
        this.loading = false
      }
    },

    async bookCabin(cabinId) {
      if (!this.currentUser) return 

      try {
       
        const response = await axios.post('/api/book', {
          cabinId: cabinId,
          userId: this.currentUser
        })

        if (response.data.success) {
        
          await this.fetchCabins()
          return { success: true, message: response.data.message }
        } else {
          return { success: false, message: response.data.message }
        }
      } catch (err) {
        return { success: false, message: 'Server error' }
      }
    }
  }
})