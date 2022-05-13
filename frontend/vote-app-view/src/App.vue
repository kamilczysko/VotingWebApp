<template>
  <main class="main">
    <nav>
      <h1 class="header">Vote App</h1>
      <div class="menu">
        <router-link to="/user-panel" class="menu-item">User panel</router-link>
        <router-link to="/" class="menu-item">Voting panel</router-link>
        <router-link to="/" class="menu-item">Print</router-link>
          
      </div>
      <div class="login-info">
            <p v-if="isLogged">You're logged in as <b>{{this.$store.state.username}}</b></p>  
            <button v-if="isLogged" v-on:click="logout">Logout</button>
        </div>
      
    </nav>
    <section>
      <router-view></router-view>
    </section>
    
  </main>
</template>

<script>
export default {
  name: 'App',
  data: () => {
    return {
      isLoggedIn: false,
      isMenuVisible: false
    } 
  },
  components: {
  },
  methods:{
    logout(){
      this.$store.commit("clear");
    },
    toggleMenu(){
      this.isMenuVisible = !this.isMenuVisible
    }
  },
  computed: {
    isLogged(){
      return this.$store.getters.isLoggedIn
    }
  }
}
</script>

<style scoped>
  .login-info {
    display: flex;
    margin-left: 50px;
    justify-content: center;
    flex-direction: column;
    align-items: center;
  }
  .menu-item {
    height: fit-content;
  }
  .menu {
    display: flex;
    justify-content: space-evenly;
    width:100%;
  }
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
    height: 10vh;
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
   height: 70vh;
   flex-direction: column;
   /* justify-content: center; */
   align-items: center;
 }
 .hidden {
   display: none;
 }
  section {
    display: flex;
    width:100%;
    height: 100%;
    justify-content: center;
    align-items: center;
  }
</style>
