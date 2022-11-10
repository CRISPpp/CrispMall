<template>
    <div class="product">
        <div class="product_info">
            <el-button @click="handleNew">新增产品</el-button>
            <el-table :data=" products.info" style="width:100%" size="small">

                <el-table-column prop="productName" label="产品名称" width="180" />
                <el-table-column prop="price" label="价格" width="180" />
                <el-table-column prop="profile" label="简介" width="180" />
                <el-table-column prop="sale" label="销量" width="180" />
                <el-table-column prop="num" label="剩余数量" width="180" />
                <el-table-column prop="icon" label="图片" min-width="20%">
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
                <el-form v-model="formData.product">
                    <el-form-item label="产品名">
                        <el-input v-model="formData.product.productName" placeholder="请输入产品名" />
                    </el-form-item>
                    <el-form-item label="价格">
                        <el-input v-model="formData.product.price" placeholder="请输入价格" type="number" />
                    </el-form-item>
                    <el-form-item label="简介">
                        <el-input v-model="formData.product.profile" placeholder="请输入简介" />
                    </el-form-item>
                    <el-form-item label="销量">
                        <el-input v-model="formData.product.sale" placeholder="请输入销量" type="number" />
                    </el-form-item>
                    <el-form-item label="剩余数量">
                        <el-input v-model="formData.product.num" placeholder="请输入剩余数量" type="number" />
                    </el-form-item>
                    <el-form-item label="图标" prop="icon">
                        <el-upload class="avatar-uploader" action="/api/oss/upload" :show-file-list="false"
                            :on-success="handleUpImage" :before-upload="beforeImageUpload">
                            <img v-if="formData.product.icon" :src="formData.product.icon" class="avatar" />
                            <div v-else class="waitUpload">点击上传图标</div>
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
import { ElNotification } from 'element-plus';
import userStore from 'vuex';
export default {
    name: 'ProductComponent',
    setup() {
        let store = userStore();
        let pageSize = ref(6);
        let page = ref(1);
        let total = ref(100);
        let products = reactive({
            info: [],
        });
        let kg = new Audio("http://119.29.100.51:11000/crispmall/kg.mp3");
        let ngm = new Audio("http://119.29.100.51:11000/crispmall/ngm.mp3");
        let handleEdit = (row) => {
            dialogFormVisible.value = true;
            formData.product.id = row.id;
            formData.product.productName = row.productName;
            formData.product.price = row.price;
            formData.product.profile = row.profile;
            formData.product.sale = row.sale;
            formData.product.num = row.num;
            formData.product.icon = row.icon;
        }
        let handlePage = (curPage) => {
            page.value = curPage;
            axios.get("/api/product", {
                params: {
                    page: page.value,
                    pageSize: pageSize.value,
                }
            }).then((response) => {
                if (response.data.code == 1) {
                    kg.play();
                    products.info = response.data.data.records;
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

        let formData = reactive({
            product: {

            },
        });

        let dialogFormVisible = ref(false);

        let handleDialogCancel = () => {
            dialogFormVisible.value = false;
        }

        let handleDialogConfirm = () => {
            dialogFormVisible.value = false;
            if (formData.product.id != null) {
                axios.put("/api/product", {
                    ...formData.product,
                    "updateBy": store.state.user.info.id

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
            else {
                axios.post("/api/product", {
                    ...formData.product,
                    "createBy": store.state.user.info.id

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
        }


        let handleNew = () => {
            dialogFormVisible.value = true;
            formData.product.id = null;
            formData.product.productName = "";
            formData.product.price = 0;
            formData.product.profile = "";
            formData.product.sale = 0;
            formData.product.num = 0;
            formData.product.icon = null;
        }

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
            formData.product.icon = res.data;
        }

        return {
            products,
            handleEdit,
            pageSize,
            page,
            total,
            handlePage,
            formData,
            handleDialogCancel,
            handleDialogConfirm,
            dialogFormVisible,
            handleNew,
            beforeImageUpload,
            handleUpImage
        }
    },
}
</script>

<style scoped>
.product {
    position: absolute;
    top: 15vh;
    left: 15vw;
    right: 0;
    padding: 10px, 0;
    margin: 0 auto;
    max-width: 100vw;
}
</style>