import { createStore } from 'vuex'
import user from './module/user'
import createPersistedstate from 'vuex-persistedstate'


export default createStore({
  state: {

  },
  getters: {
  },
  mutations: {

  },
  actions: {

  },
  modules: {
    user,
  },
  plugins: [
    createPersistedstate({
      key: 'saveInfo',
      paths: ['user', 'cart']
    })
  ]
})
