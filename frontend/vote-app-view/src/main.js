import { createApp } from 'vue'
import App from './App.vue'
import { createRouter, createWebHistory } from 'vue-router'
import { createStore } from 'vuex'

import UserForm from './components/UserForm.vue'
import CandidatesList from './components/CandidatesList.vue'

require('@/assets/css/normalize.css')
require('@/assets/css/style.css')

const routes = [
  { path: '/user-panel', component: UserForm },
  { path: '/', component: CandidatesList, name: "vote-list" },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

const store = createStore({
    state () {
      return {
        token: 0,
        username: "",
        voted: true,
        banned: true
      }
    },
    mutations: {
      login (state, data) {
        state.token = data.token
        state.username = data.username
        state.voted = data.hasVoted
        state.banned = data.isBanned
      },
      voted(state) {
        state.voted = true
      },
      clear(state){
        state.token = ""
        state.username = ""
        state.voted = true
        state.banned = true
      }
    },
    getters: {
        isLoggedIn(state){
            console.log(state.token);
            return state.token != 0
        },
        voted(state) {
            return state.voted;
        },
        banned(state) {
            return state.banned;
        }
    }
  })

createApp(App).use(router).use(store).use().mount('#app')
