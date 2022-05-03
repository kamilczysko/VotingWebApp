import { createApp } from 'vue'
import App from './App.vue'
import { createRouter, createWebHistory } from 'vue-router'

import UserForm from './components/UserForm.vue'
import CandidatesList from './components/CandidatesList.vue'

require('@/assets/css/normalize.css')
require('@/assets/css/style.css')

const routes = [
  { path: '/user-panel', component: UserForm },
  { path: '/', component: CandidatesList },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

createApp(App).use(router).use().mount('#app')
