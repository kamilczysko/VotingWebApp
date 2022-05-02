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
            <input v-model="newPasswordConfirmation" type="password" name="" id="">
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
        newPasswordConfirmation: ""
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
        width: 500px;
        height: 300px;
        display: flex;
        flex-direction: row;
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
        height: 70%;
        margin: 0px 4px;
        align-self: flex-end;
        background: #E7E3E2;
    }
    .userActionButton{
        margin-top: 25px;    
    }
</style>