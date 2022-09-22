<template>
    <div class="background">
        <div class="background_login">
            <div class="background_login_bac">
                <div class="login_tittle">CrispMallAdmin</div>
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
                <el-button type="success" class="login_button" v-if="loginFlag" @click="login">登录</el-button>
                <el-button type="success" class="login_button" v-if="!loginFlag" @click="register">注册</el-button>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue';

export default {
    name: "LoginView",
    setup() {
        let username = ref('');
        let password = ref('');
        let loginFlag = ref(true);
        let ji = new Audio("http://119.29.100.51:11000/crispmall/ji.mp3");
        let ngm = new Audio("http://119.29.100.51:11000/crispmall/ngm.mp3");
        let changeLoginFlag = () => {
            loginFlag.value = !loginFlag.value;
            ji.play();
            return;
        }
        let login = () => {
            ngm.play();
        }
        let register = () => {
            ngm.play();
        }
        return {
            username,
            password,
            loginFlag,
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