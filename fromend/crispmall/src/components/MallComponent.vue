<template>
  <div class="mall">
    <div class="light">
      <el-carousel height="30vh">
        <el-carousel-item v-for="item in products.info" :key="item">
          <el-image style="width: 30vh; height: 30vh" :src="item.icon" />
        </el-carousel-item>
      </el-carousel>
    </div>
    <div class="product_info">
      <el-table :data="products.info" style="width:100%" size="small">

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
            <el-button size="small" @click="handleEdit(scope.row)">购买</el-button>
          </template>
        </el-table-column>

      </el-table>

      <el-dialog v-model="dialogFormVisible" title="order">
        <el-form v-model="formData.product">
          <el-form-item label="购买数量">
            <el-input v-model="formData.order.num" placeholder="请输入购买数量" type="number" />
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
import axios from 'axios';
import { ElNotification } from 'element-plus';
import { reactive, ref, onMounted } from 'vue';
import { useStore } from 'vuex'

export default {
  name: 'MallComponent',
  setup() {
    let store = useStore();


    let pageSize = ref(6);
    let page = ref(1);
    let total = ref(100);
    let products = reactive({
      info: [],
    });

    let formData = reactive({
      order: {

      },
    });


    let dialogFormVisible = ref(false);

    let handleDialogCancel = () => {
      dialogFormVisible.value = false;
    }


    let handleEdit = (row) => {
      dialogFormVisible.value = true;
      formData.order.productId = row.id;
      formData.order.userId = store.state.user.info.id;
      formData.order.num = row.num;
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
          products.info = response.data.data.records;
          total.value = response.data.data.total;
        } else {
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

    let handleDialogConfirm = () => {
      axios.post("/api/order", {
        ...formData.order
      }).then((response) => {
        dialogFormVisible.value = false;
        if (response.data.code === 1) {
          ElNotification({
            title: '订单添加成功',
            message: response.data.data,
            type: 'success',
          });
          handlePage(page.value);
        }
        else {
          ElNotification({
            title: '失败',
            message: response.data.msg,
            type: 'error',
          })
        }
      })
    }

    onMounted(() => {
      handlePage(page.value);
    })


    return {
      products,
      pageSize,
      page,
      total,
      formData,
      dialogFormVisible,
      handleDialogCancel,
      handleEdit,
      handlePage,
      handleDialogConfirm,
    }
  }
}
</script>

<style scoped>
.mall {
  position: absolute;
  top: 9vh;
  left: 3vw;
  padding: 10px, 0;
  margin: 0 auto;
  max-width: 100vw;
  min-width: 80vw;
  min-height: 80vh;

  background-image: url("@/assets/img/tsk.jpg");
  background-attachment: fixed;
  background-repeat: no-repeat;
  background-size: cover;
}

.light {
  position: relative;
  height: 30vh;
  width: 30vh;
  left: 35vw;
}

.product_info {
  margin-top: 5vh;
}

.pagination {
  margin-top: 5vh;
}
</style>