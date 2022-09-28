import { createRouter, createWebHashHistory } from 'vue-router'
import  LoginView  from '@/views/LoginView.vue'
import IndexView from '@/views/IndexView.vue'
import UserComponent from '@/components/UserComponent.vue'
import ProductComponent from '@/components/ProductComponent.vue'
import OrderComponent from '@/components/OrderComponent.vue'
const routes = [
  {
    path:'/',
    name:'login',
    component: LoginView
  },
  {
    path:'/index',
    name:'index',
    component: IndexView,
    redirect: '/user',
    children:[
      {
        path:'/user',
        name:'user',
        component: UserComponent,
      },
      {
        path:'/product',
        name:'product',
        component: ProductComponent,
      },
      {
        path:'/order',
        name:'order',
        component: OrderComponent,
      },
    ],
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

export default router
