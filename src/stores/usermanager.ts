import { User, UserManager, type UserManagerSettings } from 'oidc-client-ts'
import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from 'axios'
import router from '@/router'

const settings = {
  authority: 'http://localhost:8082/realms/myrealm',
  client_id: 'myclient',
  redirect_uri: 'http://localhost:4040/signin-callback',
  popup_post_logout_redirect_uri: 'http://localhost:4040/signout-callback',
  scope: 'openid profile email roles'
} as UserManagerSettings

export const useUserManager = defineStore('usermanager', () => {
  const usermanager = new UserManager(settings)
  const authenticated = ref(false)
  const user = ref<User>()
  const roles = ref<string[]>([])
  let interceptor: number | undefined = undefined

  usermanager.events.addUserLoaded((u) => {
    userChanged(u)
  })

  usermanager.events.addAccessTokenExpired(() => {
    userChanged()
  })

  usermanager.events.addUserUnloaded(() => {
    userChanged()
  })

  function userChanged(u?: User) {
    roles.value = (u?.profile['realm_access'] as {roles: string[]} | undefined)?.roles ?? []
    authenticated.value = u !== undefined && !u.expired
    if (interceptor !== undefined) {
      axios.interceptors.request.eject(interceptor)
      interceptor = undefined
    }
    if (u !== undefined) {
      interceptor = axios.interceptors.request.use((config) => {
        config.headers.set('Authorization', `Bearer ${u.access_token}`)
        return config
      })
    }
    user.value = u
    if (u === undefined) {
      void router.push({name: 'home'})
    } else {
      void router.push({name: router.currentRoute.value.name})
    }
  }

  return {
    usermanager,
    user,
    authenticated,
    roles,
  }
})