<template>
  <main class="main">
    <nav>
      <h1 class="header">Vote App</h1>
      <button v-on:click="toggleMenu">menu</button>
      <ul class="menu" v-if="isMenuVisible">
        <li><router-link to="/user-panel">User panel</router-link></li>
        <li><router-link to="/">Voting panel</router-link></li>
        <li><router-link to="/">Print</router-link></li>
        <li></li>
      </ul>
      <div>
        <p v-if="checkLoggedIn">You're logged in as {{this.$store.state.username}}</p>  
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
  .menu {
    position: absolute;
    top: 10px;
    right: 10px;

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
