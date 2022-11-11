export default {
    // 开启命名空间
    namespaced: true,
    state: {
        info: {
            username: 'username',
            id: -1,
        }
    },
    mutations: {
        updateUsername(state, val) {
            state.info.username = val
        },
        updateId(state, val) {
            state.info.id = val
        },
    },
    actions: {

    },
    getters: {
        format(state) {
            return state.info.username + ',nice to meet you~'
          }
    }
}
