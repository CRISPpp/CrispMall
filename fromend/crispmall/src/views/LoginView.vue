<template>
    <div class="background">
        <div class="background_login">
            <div class="background_login_bac">
                <div class="login_tittle">CrispMall</div>
                <div class="login_username">
                    <el-input v-model="username" placeholder="输入账号" clearable />
                </div>
                <div class="login_password">
                    <el-input v-model="password" placeholder="输入密码" type="password" clearable />
                </div>
                <div class="change" v-if="loginFlag" @click="changeLoginFlag">
                    <p>注册</p>
                </div>
                <div class="change" v-if="!loginFlag" @click="changeLoginFlag">
                    <p>登录</p>
                </div>
                <el-button type="success" class="login_button" :loading="load" v-if="loginFlag" @click="login">登录
                </el-button>
                <el-button type="success" class="login_button" :loading="load" v-if="!loginFlag" @click="register">注册
                </el-button>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue';
import { ElNotification } from 'element-plus'
import axios from 'axios'
import router from '@/router';
import { useStore } from 'vuex';
export default {
    name: "LoginView",
    setup() {
        const store = useStore();
        let username = ref('');
        let password = ref('');
        let loginFlag = ref(true);
        let load = ref(false);
        let ji = new Audio("http://119.29.100.51:11000/crispmall/ji.mp3");
        let ngm = new Audio("http://119.29.100.51:11000/crispmall/ngm.mp3");
        let changeLoginFlag = () => {
            loginFlag.value = !loginFlag.value;
            ji.play();
            return;
        }
        let login = () => {
            load.value = true;
            axios.post('/api/user/login', {
                username: username.value,
                password: password.value,
            }).then((response) => {
                load.value = false;
                if (response.data.code === 1) {
                    store.commit('user/updateUsername', response.data.data.username);
                    store.commit('user/updateId', response.data.data.id);
                    router.push({ path: '/index' });
                    ElNotification({
                        title: '登录成功',
                        message: '登录成功',
                        type: 'success',
                    })
                }

                else {
                    ngm.play();
                    ElNotification({
                        title: '登录失败',
                        message: response.data.msg,
                        type: 'error',
                    })
                }
            }).catch(error => console.log(error));

        }
        let register = () => {
            load.value = true;
            axios.post('/api/user/register', {
                username: username.value,
                password: password.value,
            }).then((response) => {
                load.value = false;
                if (response.data.code === 1) {
                    ElNotification({
                        title: '注册成功',
                        message: response.data.data,
                        type: 'success',
                    })
                    changeLoginFlag();
                }
                else {
                    ngm.play();
                    ElNotification({
                        title: '注册失败',
                        message: response.data.msg,
                        type: 'error',
                    })
                }
            }).catch(error => console.log(error));
        }
        return {
            username,
            password,
            loginFlag,
            load,
            changeLoginFlag,
            login,
            register,
        }
    }
}
</script>

<style scoped>
.background {
    background-image: url("@/assets/img/tsk.jpg");
    background-attachment: fixed;
    background-repeat: no-repeat;
    background-size: cover;
    min-height: 300vh;
}

.background_login {
    background-image: url(@/assets/img/qsgy.jpg);
    height: 50vh;
    width: 50vw;
    background-size: cover;
    left: 50%;
    top: 50%;
    transform: translate(50%, 50%);
}

.background_login_bac {
    background-color: white;
    width: 25vw;
    height: 25vh;
    /* transform: translate(45%, 50%); */
    left: 25%;
    top: 25%;
    position: absolute;
    opacity: 0.7;
}

.login_tittle {
    text-align: center;
    color: black;
    font-size: 30px;
}

.login_username {
    display: flex;
    margin: auto;
    margin-top: 1vw;
    width: 20vw;
    align-items: center;
}

.login_password {
    display: flex;
    margin: auto;
    margin-top: 1vw;
    width: 20vw;
    align-items: center;
}

.change {
    width: 5vw;
    display: flex;
    justify-content: flex-end;
    font-size: 5px;
    cursor: pointer;
    user-select: none;
}

.login_button {
    width: 15vw;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-left: 5vw;
}
</style>