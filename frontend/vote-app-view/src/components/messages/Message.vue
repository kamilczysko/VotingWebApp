<template>
  <div class="message" v-bind:class="{error: isError}">
      <button class="close" v-on:click="closePopup">X</button>
      <p>
          {{getMessage}}
      </p>
  </div>
</template>

<script>
export default {
    name: "message",
    props: ['message', 'type', 'visible'],
    methods: {
        closePopupTimeout(){
            setTimeout(() => { 
                this.$emit('closePopup')
            }, 3*1000);
        },
        close(){
            this.$emit('closePopup')
        }
    },
    computed: {
        isError(){
            return this.type === "error"
        },
        getMessage() {
            return this.message
        }
    },
    mounted(){
        this.closePopupTimeout()
    }
}
</script>

<style scoped>
    .message {
        width: 30vw;
        height: fit-content;
        position: absolute;
        top: 15px;
        left: 50%;
        transform: translateX(-50%);
        background: #F7F8E0;
        border-color: #B4BA3A;
        border-width: 1px;
        border-radius: 15px;
        border-style: solid;
        padding-left: 15px;
        padding-right: 15px;
    }
    h1 {
        text-align: center;
        font-size: 1.1rem;
        font-weight: bolder;
    }
    .message p {
        text-align: center;
    }
    .error {
        background: #E893A9;
        border-color: #D4053A;
    }
    .close{
        position: absolute;
        top: 4px;
        right: 4px;
        margin: auto;
        border-style: none;
        background: none;
        font-weight: bolder;
    }

    .close:hover {
        font-size: 1.05rem;
        font-weight: bolder;
        cursor: pointer;
    }

    .close:active{
        top: 0.5px;
    }
    .hidden {
        display: none;
    }

</style>