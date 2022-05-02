<template>
    <form>
        <section class="login">
            <h1>Login</h1>
            <label>PESEL</label>
            <input v-model="login" type="text" name="" id="">
            <label>Password</label>
            <input v-model="password" type="password" name="" id="">
            <button class="userActionButton">Login</button>
        </section>
        <div class="separator"></div>
        <section class="register">
            <h1>Register</h1>
            <label>PESEL</label>
            <input v-model="newLogin" type="text" name="" id="">
            <label>Password</label>
            <input v-model="newPassword" type="password" name="" id="">
            <label>Confirm password</label>
            <input v-model="newPasswordConfirmation" type="password" v-bind:class="{wrong : passwordConfirmationIsWrong}">
            <button class="userActionButton">Register</button>
        </section>
    </form>
</template>

<script>
export default {
    name: "user-form",
    data: () => {
        return {
        name: "",
        password: "",
        newLogin: "",
        newPassword: "",
        newPasswordConfirmation: "",
        passwordsAreDifferent: false
        }
    },
    methods: {
        
    },
    computed: {
        passwordConfirmationIsWrong() {
            return this.newPasswordConfirmation != "" && this.newPasswordConfirmation != this.newPassword
        }
    }
}
</script>

<style scoped>
    h1 {
        text-align: center;
        font-size: 1.4em;
        justify-self: start;
        align-self: flex-start;
    }
    label {
        font-size: .9rem;
        margin-top:5px;
    }
    form {
        width: 40vw;
        height: 30vh;
        display: flex;
        flex-direction: row;
        align-items: flex-end;
        margin: auto;
    }
    section {
        display: flex;
        flex-direction: column;
        justify-content: end;
        margin: 0px 4px;
        width: 100%;
        height: 100%;
    }

    .separator {
        width: 1px;
        margin: 0px 4px;
        background: #B4ACAA;
        align-self: normal;
    }
    .userActionButton{
        margin-top: 25px;    
    }
    .wrong {
        border-color: red;
    }
    *:focus {
    outline: none;
}
</style>