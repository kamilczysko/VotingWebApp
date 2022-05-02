<template>
    <section>
        <form>
            <h1>Login</h1>
            <label>PESEL</label>
            <input v-model="login" type="text" name="" id="">
            <label>Password</label>
            <input v-model="password" type="password" name="" id="">
            <button class="userActionButton">Login</button>
        </form>
        <div class="separator"></div>
        <form>
            <h1>Register</h1>
            <label>PESEL</label>
            <input v-model="newLogin" type="text" name="" id="">
            <label>Password</label>
            <input v-model="newPassword" type="password" name="" id="">
            <label>Confirm password</label>
            <input v-model="newPasswordConfirmation" type="password" name="" id="">
            <button class="userActionButton">Register</button>
        </form>
    </section>
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

<style>
    h1{
        font-size: 1.2em;
    }
    label{
        align-self: flex-start;
        margin-left: 4px;
        margin-top: 8px;
    }
    .userActionButton{
        width: fit-content;
        margin-top: 15px;
    }
    input {
        align-items: flex-start;
        margin-bottom: 5px;
        width: 95%;
        margin-bottom: 5px;
    }
    section {
        display: flex;
        flex-direction: row;
        height: fit-content;
        width: 30vw;
        margin: auto;
    }
    form {
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 100%;
    }
    .separator {
        height: 15rem;
        width: 1px;
        background: #E7E3E2;
        margin: auto;
        margin: 1px 10px;
    }
</style>