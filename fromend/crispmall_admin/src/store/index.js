import { createStore } from 'vuex'

export default createStore({
  state: {
    name:"NameOne",
    age:100,
  },
  getters: {
  },
  mutations: {
    changeName(state){
      state.name = "hahahahahhahhhhhhhhhhhhhhhhhhhhhhhhhhhh";
    }
  },
  actions: {
    sub(hahaha){
      hahaha.commit('changeName');
    }
  },
  modules: {
  }
})
