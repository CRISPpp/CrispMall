<template>
  <el-menu default-active="0" class="el-menu-demo" mode="horizontal" :ellipsis="false">
    <router-link to="mall">
      <el-menu-item index="0">{{ username }}</el-menu-item>
    </router-link>
    <div class="flex-grow" />
    <router-link to="user">
      <el-menu-item index="1">用户信息</el-menu-item>
    </router-link>
    <el-menu-item index="2" @click="logout">登出</el-menu-item>
  </el-menu>
  <router-view />
</template>

<script>
import { useStore } from 'vuex';
import { ref } from 'vue';
import router from '@/router';
export default {
  name: "IndexView",
  setup() {
    const store = useStore();
    let username = ref(store.state.user.info.username);
    let id = ref(store.state.user.info.id);
    let logout = () => {
      router.push({ path: '/' });
    };
    return {
      username,
      id,
      logout,
    }
  },
}
</script>

<style scoped>
.flex-grow {
  flex-grow: 1;
}

a {
  text-decoration: none;
}

.el-menu-demo {
  background-color: lightblue;
}
</style>