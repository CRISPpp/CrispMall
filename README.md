## 计科 2 班

## 学号:202030443387

## 姓名:张博

1.	系统设计：
前端：
vue3
ui使用框架elementplus
静态路由vue-router
持久化存储vuex, persistedstate
后端：
Springcloud
服务注册中心和配置中心：nacos
后端网关：springgateway
数据库：mysql
缓存以及非关系型数据库：redis
Oss：minio
远程调用：openfeign

系统架构图：
 
![image](https://user-images.githubusercontent.com/78430796/220146275-fbd829b9-0d3d-4035-b5d2-a6f88bc2d95f.png)



前端CrispMallAdmin管理系统部分：
![image](https://user-images.githubusercontent.com/78430796/220146483-37ce338b-d529-46c7-ac67-db95ad71a8e3.png)
登录界面布局相关代码：主要组件为账号密码输入框，注册、登录、切换按钮。
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
                <el-button type="success" class="login_button" :loading="load" v-if="loginFlag" @click="login">登录
                </el-button>
                <el-button type="success" class="login_button" :loading="load" v-if="!loginFlag" @click="register">注册
                </el-button>
            </div>
登录功能关键代码：逻辑为向后端发送登录请求，之后根据后端返回的状态码判断是否登录成功，成功则通过静态路由进行跳转到index界面，否则弹出错误。
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
注册功能关键代码：逻辑为向后端发送登录请求，之后根据后端返回的状态码判断是否登录成功，成功则通过静态路由进行跳转到login界面，否则弹出错误。
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
![image](https://user-images.githubusercontent.com/78430796/220146541-4a39e68b-583e-4188-b1f2-5f1da1fdda5b.png)
IndexView界面关键代码：主要部分为侧面导航栏，导航栏包括用户管理、商品管理和订单管理按钮，头部导航栏则有登出按钮，中间部分为子路由内容，子路由组件包括用户组件UserComponent，商品组件ProductComponent以及订单组件OrderComponent
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
![image](https://user-images.githubusercontent.com/78430796/220146576-e5208cb4-1c8e-403a-b302-afd2107bc9b6.png)
用户组件UserComponent布局关键代码：主要组件为form表单，对用户的用户名、电话、邮箱、地址、权限以及头像信息进行展示，同时有编辑按钮可以进行编辑。
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
![image](https://user-images.githubusercontent.com/78430796/220146608-1f6e401f-d754-4d83-a3c9-e2c7e4eacc8a.png)
编辑页面布局关键代码：主要为输入框，分别可以修改用户的各种相关信息，同时可调用后端的oss服务对头像进行上传。
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
分页查询关键代码以及逻辑：根据用选择的页数进行分页查询，成功则将数据保存到user中，渲染到界面，同时读取用户总数等内容。
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
编辑用户关键代码以及逻辑：将用户的所有信息打包成json向后端发送post请求， 成功则修改user数据，同时调用分页查询请求，对页面进行重新渲染。
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
上传图片关键代码以及逻辑：上传前检查图片大小是否超过10M，超过则返回错误，否则进行上传，上传结束后将返回的结果渲染到页面。
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
![image](https://user-images.githubusercontent.com/78430796/220146657-a53f17f6-aaaa-4f9a-bee0-204dfdf799f4.png)
商品组件布局关键代码：主要为列表，新增商品按钮，展示商品的名称、价格、销量、库存、图标以及编辑按钮
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
                        <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
                    </template>
                </el-table-column>

            </el-table>
![image](https://user-images.githubusercontent.com/78430796/220146682-426e5f4f-846b-4781-8000-d3433ca50772.png)
商品编辑界面关键代码：有产品各种信息的输入修改框，oss上传图标，以及提交后端的按钮。
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
分页查询、上传oss、修改信息同用户组件，新增的关键代码和主要逻辑为：将表单内容至为空，之后通过表单组件进行提交创建。
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

axios.post("/api/product", {
                    ...formData.product,

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
![image](https://user-images.githubusercontent.com/78430796/220146721-71a8eda0-0c11-4598-8574-e889df0f1639.png)
订单组件布局关键代码：主要展示订单的用户id，用户名、产品id、产品名、购买数量以及购买日期。
<el-table :data=" orders.info" style="width:100%" size="small">

                <el-table-column prop="userId" label="用户id" width="180" />
                <el-table-column prop="username" label="用户名称" width="180" />
                <el-table-column prop="productId" label="产品id" width="180" />
                <el-table-column prop="productName" label="产品名称" width="180" />
                <el-table-column prop="num" label="购买数量" width="180" />
                <el-table-column prop="createTime" label="创建时间" min-width="180%" />


            </el-table>




前端CrispMall商城系统部分：

![image](https://user-images.githubusercontent.com/78430796/220146739-0d285aa8-664c-4803-9515-cdece8587792.png)
Login页面同管理系统，index页面布局关键代码：上部导航栏为用户名，点击可进入商城主页，右方分别为用户信息按钮和登出按钮，下方为跑马灯展示商品，最下方为商品详细信息。
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
![image](https://user-images.githubusercontent.com/78430796/220146753-aa999e18-f52f-4c82-96ea-4db1c36685d3.png)
用户信息界关键代码：上部分的个人信息以及编辑界面同管理系统，下方多出个人订单的分页查询。
<el-table :data="user.info" style="width:100%" size="small">

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

<el-table :data="order.info" style="width:100%" size="small">

        <el-table-column prop="userId" label="用户id" width="180" />
        <el-table-column prop="username" label="用户名称" width="180" />
        <el-table-column prop="productId" label="产品id" width="180" />
        <el-table-column prop="productName" label="产品名称" width="180" />
        <el-table-column prop="num" label="购买数量" width="180" />
        <el-table-column prop="createTime" label="创建时间" min-width="180%" />


      </el-table>
后端部分：
网关模块：
![image](https://user-images.githubusercontent.com/78430796/220146778-639d5f61-5f19-4278-aa49-53d7fbbf9535.png)
网关关键代码：
网关的主要功能为将前端各个请求分配给对应的模块，同时做到负载均衡，这里将/user/的请求负载均衡到CrispMallUser的服务，将/product/的请求负载均衡到CrispMallProduct的服务，将/order/的请求负载均衡到CrispMallOrder的服务，将/oss/的请求负载均衡到CrispMallOss的服务。
      routes:
        - id: user_route
          uri: lb://CrispMallUser
          predicates:
            - Path=/user/**

        - id: order_route
          uri: lb://CrispMallOrder
          predicates:
            - Path=/order/**

        - id: product_route
          uri: lb://CrispMallProduct
          predicates:
            - Path=/product/**

        - id: oss_route
          uri: lb://CrispMallOss
          predicates:
            - Path=/oss/**
用户模块：
![image](https://user-images.githubusercontent.com/78430796/220146797-dd2e6574-9944-4488-8f5b-0ead10ac0939.png)
 配置部分主要配置了数据库crud间的信息填充、分页器、乐观锁等。
关键代码：
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "isDeleted", Integer.class, 0);
        this.strictInsertFill(metaObject, "version", Long.class, 1L);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject,"updateTime", LocalDateTime.class, LocalDateTime.now());
    }

控制层：
订单模块服务：
基于mybatis-plus建立和云端数据库的连接完成读写，再通过feign调用远程商品和用户服务的接口
![image](https://user-images.githubusercontent.com/78430796/220146815-4c57e004-069e-49f5-96a1-45e1f0931a67.png)
![image](https://user-images.githubusercontent.com/78430796/220146819-b68367c1-73c5-4046-bfd4-8810b829e0d7.png)
提供了分页查询订单的接口，查询都会先查询缓存是否存在，存在则返回缓存的数据，否则再去查询数据库，写入缓存再返回。
![image](https://user-images.githubusercontent.com/78430796/220146861-bbe21000-d21b-4385-88c3-4b30e3894f1e.png)
提供了添加订单的接口
![image](https://user-images.githubusercontent.com/78430796/220146896-cdcf397a-37f4-4c66-8f31-7bcf343ab75f.png)
提供分页查询用户订单接口
![image](https://user-images.githubusercontent.com/78430796/220146921-f2b97233-b24a-4707-bf02-9a2e6c9025ce.png)
Oss模块主要提供对分布式文件系统的连接
提供了上传文件的接口，上传完成后会返回文件的url
![image](https://user-images.githubusercontent.com/78430796/220146950-a8370ab2-fab1-4cec-a7b7-9257a359629b.png)
产品模块主要提供了分页查询产品接口
![image](https://user-images.githubusercontent.com/78430796/220146985-b492fd61-746c-47b7-b293-5040f7c9eaeb.png)
根据id获取产品信息
![image](https://user-images.githubusercontent.com/78430796/220147014-289d3566-4526-4ddc-91e6-e2e8ad15d3f7.png)
修改产品信息
![image](https://user-images.githubusercontent.com/78430796/220147032-0739e3e1-da94-4e89-b7ea-3fcdec804d83.png)
新增商品
![image](https://user-images.githubusercontent.com/78430796/220147045-9ebd5ccd-bee6-4338-89d9-c57293d0940f.png)
用户模块主要提供了获取用户接口
![image](https://user-images.githubusercontent.com/78430796/220147068-b733f531-ef80-43ca-9a41-ad435577713a.png)
根据id获取用户
![image](https://user-images.githubusercontent.com/78430796/220147088-f2cd0d70-d454-47a6-90b7-06e1861e6e54.png)
登录接口
![image](https://user-images.githubusercontent.com/78430796/220147112-205e911b-cde1-4990-bfcb-d76f2ad26f2b.png)
注册接口
![image](https://user-images.githubusercontent.com/78430796/220147126-525b3f1c-c471-4921-a66f-74453f15d546.png)
修改用户接口
![image](https://user-images.githubusercontent.com/78430796/220147149-57d75dac-9a8c-4182-b715-fc16b1dbd07b.png)
3.	功能测试：
用户端打开界面后会进入到登录界面
![image](https://user-images.githubusercontent.com/78430796/220147172-e0f5385b-8b91-4937-bdce-0d5b91c744cb.png)
注册功能：
![image](https://user-images.githubusercontent.com/78430796/220147189-9d1b1224-0661-4180-bb65-f57c6446864d.png)
填入用户名和密码后可以进行注册，注册成功或者错误都会有相应的提示
![image](https://user-images.githubusercontent.com/78430796/220147217-1861a55a-2b8c-4aee-a30e-f42a2f7db4bc.png)
登录成功后进入商品界面
![image](https://user-images.githubusercontent.com/78430796/220147248-477852bf-8eb8-4098-9b15-7824e56828bb.png)
导航栏的用户名对应商城界面，上方会滚动播放商品的图片，下方显示展示商品的信息以及分页条，点击分页条可进入相应的商品页面，想要购买则直接点击即可，购买视图
![image](https://user-images.githubusercontent.com/78430796/220147269-3fa79b05-f29d-41cd-9242-4af0751f42bb.png)
购买后后端会检测购买的数量是否合法，合法则会返回成功并修改商品数据
![image](https://user-images.githubusercontent.com/78430796/220147297-d366e3c1-6c65-4c63-b0bb-d9845869027a.png)
之后会在邮箱收到信息：
![image](https://user-images.githubusercontent.com/78430796/220147321-b41d2fb0-f458-4085-a161-02ea94a8036d.png)
点击用户信息可进入用户界面，展示本用户的详细信息以及订单信息
![image](https://user-images.githubusercontent.com/78430796/220147343-f9c84d30-9d5a-4bce-be42-b9468c76884b.png)
点击编辑可编辑用户的详细信息，包括修改头像等
![image](https://user-images.githubusercontent.com/78430796/220147370-995c3064-449c-4ca9-a443-6ae89c667e3b.png)

点击登出即可返回到登录界面
![image](https://user-images.githubusercontent.com/78430796/220147396-233e498b-d710-4736-8571-26a6cd4e20d8.png)
管理端
登录界面与用户端相同，不过如果登录的用户不是管理员会拒绝服务
![image](https://user-images.githubusercontent.com/78430796/220147417-1eb1d339-c9a3-4690-9192-a5aa7c49f9d1.png)
登录之后导航栏在侧面，可管理用户、商品以及查看所有订单信息
![image](https://user-images.githubusercontent.com/78430796/220147432-4287fe97-7314-4480-86f6-184eeb4d50fc.png)
用户管理界面除了能看到所有用户的信息外，还能修改每个用户的信息
![image](https://user-images.githubusercontent.com/78430796/220147474-fed0b9ac-2651-4602-a73e-67c285c19821.png)
商品界面可以新增商品，修改商品信息
![image](https://user-images.githubusercontent.com/78430796/220147511-7abc3c16-4e63-4695-913d-23f3ad1f4e2c.png)
![image](https://user-images.githubusercontent.com/78430796/220147523-f3ca81b8-e3b7-4ed1-a16e-dbe6224e58d1.png)
订单界面则展示所有用户的订单信息
![image](https://user-images.githubusercontent.com/78430796/220147554-192f586e-df23-466a-8573-ea1c7b7fc475.png)
上方的登出也会退出到登录页面



