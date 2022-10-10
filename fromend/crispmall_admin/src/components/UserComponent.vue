<template>
    <div class="user">
        <div class="user_info">
            <el-table :data="users.info" style="width:100%" size="small">

                <el-table-column prop="username" label="用户名" width="180" />
                <el-table-column prop="phone" label="电话" width="180" />
                <el-table-column prop="mail" label="邮箱" width="180" />
                <el-table-column prop="position" label="地址" width="180" />
                <el-table-column prop="role" label="权限" width="180" />
                <el-table-column prop="icon" label="头像" min-width="20%">
                    <!-- 图片的显示 -->
                    <template v-slot="scope">
                        <img :src="scope.row.icon" min-width="70" height="70" />
                    </template>
                </el-table-column>

                <el-table-column label="操作" min-width="10%">
                    <template v-slot="scope">
                        <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <el-dialog v-model="dialogFormVisible" title="Shipping address">
                <el-form v-model="formData.user">
                    <el-form-item label="用户名">
                        <el-input v-model="formData.user.username" placeholder="请输入用户名" />
                    </el-form-item>
                    <el-form-item label="密码">
                        <el-input v-model="formData.user.password" placeholder="请输入密码" />
                    </el-form-item>
                    <el-form-item label="邮箱">
                        <el-input v-model="formData.user.mail" placeholder="请输入邮箱" />
                    </el-form-item>
                    <el-form-item label="位置">
                        <el-input v-model="formData.user.position" placeholder="请输入位置" />
                    </el-form-item>
                    <el-form-item label="权限">
                        <el-input v-model="formData.user.role" placeholder="请输入权限, admin 或者 normal" />
                    </el-form-item>
                    <el-form-item label="头像" prop="icon">
                        <el-upload class="avatar-uploader" action="/api/oss/upload" :show-file-list="false"
                            :on-success="handleUpImage" :before-upload="beforeImageUpload">
                            <img v-if="formData.user.icon" :src="formData.user.icon" class="avatar" />
                            <div v-else class="waitUpload">点击上传头像</div>
                        </el-upload>
                    </el-form-item>
                </el-form>

                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="handleDialogCancel">取消</el-button>
                        <el-button type="primary" @click="handleDialogConfirm">确认</el-button>
                    </span>
                </template>
            </el-dialog>

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
        let dialogFormVisible = ref(false);
        let pageSize = ref(6);
        let page = ref(1);
        let total = ref(100);
        let users = reactive({
            info: [],
        });
        let kg = new Audio("http://119.29.100.51:11000/crispmall/kg.mp3");
        let ngm = new Audio("http://119.29.100.51:11000/crispmall/ngm.mp3");
        let handleEdit = (row) => {
            formData.user.id = row.id;
            formData.user.username = row.username;
            formData.user.password = row.password;
            formData.user.phone = row.phone;
            formData.user.mail = row.mail;
            formData.user.position = row.position;
            formData.user.icon = row.icon;
            formData.user.role = row.role;
            dialogFormVisible.value = true;
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

        let formData = reactive({
            user: {

            },
        });

        let handleDialogCancel = () => {
            dialogFormVisible.value = false;
        }

        let handleDialogConfirm = () => {
            dialogFormVisible.value = false;
            axios.put("/api/user", {
                ...formData.user,
            }).then((response) => {
                if (response.data.code === 1) {
                    ElNotification({
                        title: '修改成功',
                        message: response.data.data,
                        type: 'success',
                    })
                    handlePage(page.value);
                }
                else {
                    ngm.play();
                    ElNotification({
                        title: '修改失败',
                        message: response.data.msg,
                        type: 'error',
                    })
                }
            }).catch(error => console.log(error));
        }

        onMounted(() => {
            handlePage(page.value);
        })


        let beforeImageUpload = (rawFile) => {
            if (rawFile.size / 1024 / 1024 > 10) {
                ElNotification({
                    title: '上传失败',
                    message: '文件大小超过10MB',
                    type: 'error',
                })
                return false;
            }
            return true;
        }
        let handleUpImage = (res) => {
            formData.user.icon = res.data;
        }


        return {
            users,
            handleEdit,
            pageSize,
            page,
            total,
            handlePage,
            dialogFormVisible,
            formData,
            handleDialogCancel,
            handleDialogConfirm,
            beforeImageUpload,
            handleUpImage,
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

.waitUpload {
    background-color: aqua;
}
</style>