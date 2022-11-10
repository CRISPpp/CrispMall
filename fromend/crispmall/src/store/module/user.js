export default {
    // 开启命名空间
    namespaced: true,
    state: {
        info: {
            username: 'username',
            userId: -1,
        }
    },
    mutations: {
        updateUsername(state, val) {
            state.info.username = val
        },
        updateId(state, val) {
            state.info.userId = val
        },
    },
    actions: {

    },
    getters: {

    }
}
