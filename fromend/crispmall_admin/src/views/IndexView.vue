<template>
    <div class="top">
        <div class="top_tittle">
            CrispMallAdmin
        </div>
        <el-button type="primary" class="top_button" @click="logout">登出</el-button>
    </div>
    <div class="sidebar">
        <el-menu default-active="1" class="sidebar_menu">
            <router-link to="user">
                <el-menu-item index="1">用户管理</el-menu-item>
            </router-link>
            <router-link to="product">
                <el-menu-item index="2">商品管理</el-menu-item>
            </router-link>
            <router-link to="order">
                <el-menu-item index="3">订单信息</el-menu-item>
            </router-link>
        </el-menu>
        <router-view />
        <div class="xkz"></div>
    </div>
</template>
    
<script>
import router from '@/router';

export default {
    name: "IndexView",
    setup() {
        let logout = () => {
            router.push({ path: '/' });
        };
        return {
            logout,
        }
    },
}
</script>
    
<style scoped>
a {
    text-decoration: none;
}

.top {
    height: 10vh;
    background-image: url(@/assets/img/tsk.jpg);
    font-size: 30px;
    text-align: center;
    font-weight: bold;
    left: 0;
    top: 0;
}

.xkz {
    position: absolute;
    bottom: 0;
    right: 0;
    background-image: url("@/assets/img/xkz.jpg");
    background-repeat: no-repeat;
    min-height: 20vh;
    min-width: 20vw;
}

.sidebar_menu {
    position: fixed;
    left: 0;
    background-color: aliceblue;
    width: 10vw;
    height: 100vh;
    font-size: 16px;
    overflow: auto;
}
</style>