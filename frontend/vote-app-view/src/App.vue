<template>
  <main class="main">
    <nav>
      <h1>Vote App</h1>
    </nav>
    <UserForm/>
  </main>
</template>

<script>
import UserForm from "./components/UserForm.vue"
export default {
  name: 'App',
  components: {
    UserForm
  }
}
</script>

<style scoped>
  h1 {
    text-align: center;
  }
 main {
   display: flex;
   height: 80vh;
   flex-direction: column;
 }
</style>
