<template>
  <main class="main">
    <nav>
      <h1 class="header">Vote App</h1>
      <router-link to="/user-panel">User panel</router-link>
      <router-link to="/">Voting panel</router-link>
      <router-link to="/">Print</router-link>
      <button v-if="isLoggedIn" v-on:click="logout">Logout</button>
      <p v-if="isLoggedIn">You're logged in as {{this.$store.state.username}}</p>
    </nav>
    <router-view></router-view>
  </main>
</template>

<script>
export default {
  name: 'App',
  components: {
  },
  methods:{
    logout(){
      this.$store.commit("clear");
    }
  },
  computed: {
    isLoggedIn(){
      return this.$store.getters.isLoggedIn
    }
  }
}
</script>

<style scoped>
  button {
    border:none;
    background: none;
  }
  nav {
    display: grid;
    grid-template-columns: 2fr 2fr 1fr 1fr;
    width: 100%;
    height: 10vh;
  }
  nav a {
    grid-column: 4;
    display: flex;
    justify-content: center;
    align-self: center;
    text-decoration: none;
    color: black;
  }
    nav a:hover {
    font-weight: bolder;
    position: relative;
    bottom: 2px;
    left: 2px;
  }
  nav a:active {
    position: relative;
    top: .1px;
    font-weight: bolder;
  }
  nav h1 {
    grid-column: 2;
    display: flex;
    align-self: center;
    justify-content: center;
  }
 main {
   display: flex;
   height: 80vh;
   flex-direction: column;
   justify-content: center;
   align-items: center;
 }
 .hidden {
   display: none;
 }

</style>
