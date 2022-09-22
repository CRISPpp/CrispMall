<template>
  <div class="fback">这是父组件
    <div class="fOne">{{p1}}</div>
  </div>
  <component-one />
</template>

<script>
import componentOne from '@/components/componentOne.vue';
import { computed } from 'vue';
import { useStore } from 'vuex';
export default {
  name: "HomeView",
  setup() {
    let store = useStore();
    let p1 = computed(() => {
      return store.state.name + store.state.age
    });
    return {
      p1,
    }
  },
  components: { componentOne },

}
</script>

<style scoped>
.fback {
  background-color: turquoise;
}
</style>