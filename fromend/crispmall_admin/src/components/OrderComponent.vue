<template>
    <div class="order">
        <div class="order_info">
            <el-table :data=" orders.info" style="width:100%" size="small">

                <el-table-column prop="userId" label="用户id" width="180" />
                <el-table-column prop="username" label="用户名称" width="180" />
                <el-table-column prop="productId" label="产品id" width="180" />
                <el-table-column prop="productName" label="产品名称" width="180" />
                <el-table-column prop="num" label="购买数量" width="180" />
                <el-table-column prop="createTime" label="创建时间" min-width="180%" />


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
    name: 'ProductComponent',
    setup() {
        let pageSize = ref(20);
        let page = ref(1);
        let total = ref(100);
        let orders = reactive({
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
            axios.get("/api/order", {
                params: {
                    page: page.value,
                    pageSize: pageSize.value,
                }
            }).then((response) => {
                if (response.data.code == 1) {
                    kg.play();
                    orders.info = response.data.data.records;
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
            orders,
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
.order {
    position: absolute;
    top: 15vh;
    left: 15vw;
    right: 0;
    padding: 10px, 0;
    margin: 0 auto;
    max-width: 100vw;
}
</style>