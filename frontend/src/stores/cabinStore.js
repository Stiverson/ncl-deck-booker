import { defineStore } from 'pinia'
import axios from 'axios'

export const useCabinStore = defineStore('cabin', {
  state: () => ({
    cabins: {}, 
    loading: false,
    error: null,
    currentUser: 'Stiverson'
  }),

  actions: {
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
      try {
        const response = await axios.post('/api/book', {
          cabinId: cabinId,
          userId: this.currentUser
        })

        if (response.data.success) {
          alert(`Sucesso: ${response.data.message}`)
          await this.fetchCabins() 
        } else {
          alert(`Falha: ${response.data.message}`)
        }
      } catch (err) {
        alert('Erro de conexão com o servidor.')
      }
    }
  }
})