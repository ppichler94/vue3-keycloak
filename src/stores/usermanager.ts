import {
  User,
  UserManager,
  type UserManagerSettings
} from 'oidc-client-ts'
import { defineStore } from 'pinia'
import { ref } from 'vue'

const settings = {
  authority: 'http://localhost:8082/realms/myrealm',
  client_id: 'myclient',
  redirect_uri: 'http://localhost:4040/signin-callback',
  popup_post_logout_redirect_uri: 'http://localhost:4040/signout-callback',
} as UserManagerSettings

export const useUserManager = defineStore('usermanager', () => {
  const usermanager = new UserManager(settings)
  const authenticated = ref(false)
  const user = ref<User>()

  usermanager.events.addUserLoaded((u) => {
    user.value = u
    authenticated.value = u && !u.expired
  })

  usermanager.events.addAccessTokenExpired(() => {
    user.value = undefined
    authenticated.value = false
  })

  usermanager.events.addUserUnloaded(() => {
    authenticated.value = false
    user.value = undefined
  })

  return {
    usermanager,
    user,
    authenticated,
  }
})