<template>
  <main class="main">
    <nav>
      <h1 class="header">Vote App</h1>
      <a class="hidden" href="#">Show candidates</a>
      <a href="#">Logout</a>
      <a class="hidden" href="#">User panel</a>
    </nav>
    <!-- <UserForm/> -->
    <CandidatesList/>
  </main>
</template>

<script>
// import UserForm from "./components/UserForm.vue"
import CandidatesList from "./components/CandidatesList.vue"
export default {
  name: 'App',
  components: {
    // UserForm,
    CandidatesList
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
