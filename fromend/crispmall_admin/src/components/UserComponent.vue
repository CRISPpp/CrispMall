<template>
    <div class="user">
        <div class="user_info">
            <el-table :data="users.info" style="width:100%" size="small">

                <el-table-column prop="username" label="用户名" width="180" />
                <el-table-column prop="phone" label="电话" width="180" />
                <el-table-column prop="mail" label="邮箱" width="180" />
                <el-table-column prop="position" label="地址" width="180" />
                <el-table-column prop="icon" label="头像" min-width="20%">
                    <!-- 图片的显示 -->
                    <template v-slot="scope">
                        <img :src="scope.row.icon" min-width="70" height="70" />
                    </template>
                </el-table-column>

                <el-table-column label="操作" min-width="10%">
                    <template v-slot="scope">
                        <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    </template>
                </el-table-column>

            </el-table>

            <div class="pagination">
                <el-pagination layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page="page"
                    @current-change="handlePage"></el-pagination>
            </div>
        </div>
    </div>

</template>

<script>
import { onMounted, reactive, ref, } from 'vue';
import axios from 'axios';
import { ElNotification } from 'element-plus'
export default {
    name: 'UserComponent',
    setup() {
        let pageSize = ref(6);
        let page = ref(1);
        let total = ref(100);
        let users = reactive({
            info: [],
        });
        let kg = new Audio("http://119.29.100.51:11000/crispmall/kg.mp3");
        let ngm = new Audio("http://119.29.100.51:11000/crispmall/ngm.mp3");
        let handleEdit = (index, row) => {
            console.log(index);
            console.log(row)
        }
        let handlePage = (curPage) => {
            page.value = curPage;
            axios.get("/api/user", {
                params: {
                    page: page.value,
                    pageSize: pageSize.value,
                }
            }).then((response) => {
                if (response.data.code == 1) {
                    kg.play();
                    users.info = response.data.data.records;
                    total.value = response.data.data.total;
                } else {
                    ngm.play();
                    ElNotification({
                        title: '失败',
                        message: response.data.msg,
                        type: 'error',
                    })
                }
            }).catch((error) => {
                console.log(error);
            })
        }
        onMounted(() => {
            handlePage(page.value);
        })
        return {
            users,
            handleEdit,
            pageSize,
            page,
            total,
            handlePage,
        }
    },
}
</script>

<style scoped>
.user {
    position: absolute;
    top: 15vh;
    left: 15vw;
    right: 0;
    padding: 10px, 0;
    margin: 0 auto;
    max-width: 100vw;
}
</style>