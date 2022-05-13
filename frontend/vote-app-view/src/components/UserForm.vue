<template>

    <div>
        <Message v-bind:message="testMessage" v-bind:type="type" v-if="isMessageVisible" v-on:closePopup="closePopup"/>
        <form action="javascript:void(0);">
            <section class="login">
                <h1>Login</h1>
                <label>PESEL</label>
                <input v-model="name" type="text" name="" id="">
                <label>Password</label>
                <input v-model="password" type="password" name="" id="">
                <button class="userActionButton" v-on:click="performLogin">Login</button>
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
                <button class="userActionButton" v-on:click="registerAction">Register</button>
            </section>
        </form>
    </div>
</template>

<script>
import axios from 'axios'
import Message from './messages/Message.vue'
export default {
    name: "user-form",
    components: {
        Message
    },
    data: () => {
        return {
        name: "",
        password: "",
        newLogin: "",
        newPassword: "",
        newPasswordConfirmation: "",
        passwordsAreDifferent: false,
        testMessage: "",
        type: "error",
        isMessageVisible: false
        }
    },
    methods: {
        performLogin() {
            axios.post('/rest/auth', {username: this.name, password: this.password})
            .then(response => response.data)
            .then(data => this.$store.commit("login", {"username": data.username, "token": data.token, "isBanned": data.banned, "hasVoted": data.hasVoted}))
            .then(() => this.setMessage("Logged in sucessfully", "msg"))
            .then(() => {
                this.name = "";
                this.password = "";
            })
            .then(() => {
                this.$router.push('/') 
            })
            .catch(() => this.setMessage("Failed to login", "error"))
        },
        setMessage(msg, tp){
            this.type = tp
            this.testMessage = msg
            this.isMessageVisible = true;
         },
         closePopup(){
             this.isMessageVisible = false;
         },
         registerAction() {
            if (this.newPasswordConfirmation != "" && this.newPasswordConfirmation == this.newPassword) {
                const validation = this.validateUserLogin();
                console.log(validation)
                if(!validation == "") {
                    this.setMessage(validation, "error");
                    return;
                }
                axios.post('rest/register', {
                    identityNumber: this.newLogin,
                    password: this.newPassword
                })
                .then(() => {
                    this.setMessage("Regiser success", "msg");
                    this.newLogin = "";
                    this.newPassword = "";
                    this.newPasswordConfirmation = "";
                })
                .catch(() => this.setMessage("Failed to register", "error"));
            }
         },
         validateUserLogin() {
             const reg = new RegExp('^([0-9]{11})$');
             if(!reg.test(this.newLogin)) {
                 return "Proper id number consist of 11 numbers";
             }
             if (this.newLogin[0] == '0') {
                 return "You probably ain't alive";
             }
             return "";
         }
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