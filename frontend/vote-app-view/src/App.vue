<template>
  <body>
    <UserForm/>
  </body>
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

<style>
  body {
    height: 80vh;
    width: 100vw;
    display: flex;
    align-content: center;
  }
</style>
