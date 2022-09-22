<template>
    <div class="back">子组件
        <div class="sOne">{{p1}}</div>
        <button @click="btn">改变仓库</button>
    </div>
</template>

<script>
import { useStore } from 'vuex';

export default {
    name: "componentOne",
    setup() {
        let store = useStore();
        let p1 = store.state.name;
        let btn = () => {
            store.dispatch('sub');
        };
        return {
            btn,
            p1,
        }
    }
}
</script>

<style scoped>
.back {
    background-color: antiquewhite;
}
</style>