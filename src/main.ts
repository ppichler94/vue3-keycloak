import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { useUserManager } from '@/stores/usermanager'

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')

const usermanager = useUserManager().usermanager
usermanager.events.addUserLoaded((user) => {
  console.log("user loaded")
  console.log(`expires in: ${user.expires_in}`)
})