<template>
        <li class="party">
           <button v-on:click="expand"> {{partyName}}</button>
            <ul v-if="isCollapsed">
                <li v-for="c in candidates" v-bind:key="c.id"><button v-on:click="vote(c.id)">{{c.name}}</button></li>
            </ul>
        </li>
</template>

<script>
export default {
    name: "candidates-from-party",
    props: ["partyName", "candidates"],
    data: ()=>{
        return {
            isCollapsed: false
        }
    },
    methods: {
        expand() {
            this.isCollapsed = !this.isCollapsed
        },
        vote(id){
            this.$emit("vote", id)
        }
    }
}
</script>

<style>
    button {
        border: none;
        background: none;
        cursor: pointer;
    }
    button:hover {
        font-weight: bolder;
        position: relative;
        top: 1px;
    }
    li{
        margin-top: 4px;
        list-style: none;
        display: flex;
        justify-content: space-between;
        border-bottom: 1px;
        border-color: #B4ACAA;
        border-bottom-style: solid;
        
    }
    .party {
        width: 400px;
    }
    ul{
        font-size: 1.1rem;
        padding: 0;
        width: 25vw;
    }
</style>