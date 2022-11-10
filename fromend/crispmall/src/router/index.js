import { createRouter, createWebHashHistory } from 'vue-router'
import LoginView from '@/views/LoginView.vue'
import IndexView from '@/views/IndexView.vue'
import UserComponent from '@/components/UserComponent.vue'
import MallComponent from '@/components/MallComponent.vue'
const routes = [
  {
    path: '/',
    name: 'login',
    component: LoginView
  },
  {
    path: '/index',
    name: 'index',
    component: IndexView,
    redirect: '/mall',
    children: [
      {
        path: '/user',
        name: 'user',
        component: UserComponent,
      },
      {
        path: '/mall',
        name: 'mall',
        component: MallComponent,
      },
    ],
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

export default router
