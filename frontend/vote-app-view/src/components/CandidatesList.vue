<template>
    <main>
        <Message v-bind:message="testMessage" v-bind:type="type" v-if="isMessageVisible" v-on:closePopup="closePopup"/>
        <section>
            <div class="voteList">
                <h2>Vote:</h2>
                <ul class="party">
                    <CandidatesFromParty v-for="[party, listOfCandidates] in getPartyToCandidates" v-bind:key="party"
                        v-bind:partyName="party"
                        v-bind:candidates="listOfCandidates"
                        v-on:vote="vote"/>
                </ul>
            </div>
            <div>
                <Bar :chart-data="getChartData" :chart-options="chartOptions"/>
                <Bar :chart-data="getPartyChartData" :chart-options="chartOptions"/>
            </div>
        </section>
        <p>Total votes: {{getTotal}}</p>
    </main>
</template>

<script>
import { Bar } from 'vue-chartjs'
import Message from './messages/Message.vue'
import CandidatesFromParty from './list/CandidatesFromParty.vue'
import axios from 'axios'

import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
    name: "candidate-list",
    components: {
        Bar, Message, CandidatesFromParty
    },
    data: () => {
        return {
            candidates: [],
            chartOptions: {
                responsive: true,
                maintainAspectRatio: false
            },
            testMessage: "",
            type: "error",
            isMessageVisible: false,
            isCollapsed: false
        }
    },
    methods:{
        getTotalNumberOfVotes(){
            this.candidates.array.forEach(element => {
                this.totalNumberOfVotes += element.votes
            });
            return this.totalNumberOfVotes;
        },
         generateRandomColor(){
            let maxVal = 0xFFFFFF;
            let randomNumber = Math.random() * maxVal; 
            randomNumber = Math.floor(randomNumber);
            randomNumber = randomNumber.toString(16);
            let randColor = randomNumber.padStart(6, 0);   
            return `#${randColor.toUpperCase()}`
         },
         vote(id){
             if(!this.$store.getters.isLoggedIn){
                this.setMessage("You need to login first", "error")
                return;
             }
             if(this.$store.getters.voted){
                 this.setMessage("You have voted already!", "error")
                 return;
             }
             if(this.$store.getters.banned){
                 this.setMessage("You are banned from voting!", "error")
                 return;
             }
             const candidate = this.candidates.filter(candidate => candidate.id === id)
             const options = {"headers" : {"Authorization": "Bearer "+this.$store.state.token}}
             axios.post("/rest/vote/"+id,{}, options)
             .then(() => this.voteOnCandidate(candidate[0]))
             .catch((error) => this.setMessage("There is problem with your vote: "+error, "error"))
             this.$emit("candidatesUpdate", this.candidates)
         },
         closePopup(){
             this.isMessageVisible = false;
         },
         setMessage(msg, tp){
            this.type = tp
            this.testMessage = msg
            this.isMessageVisible = true;
         },
         voteOnCandidate(candidate){
            this.setMessage("Voted on "+candidate.name, "msg")
            this.$store.commit("voted")
            candidate.votes = candidate.votes+1
         },
         expand(){
             this.isCollapsed = !this.isCollapsed
         },
         getPartyToCandidatesMap() {
            const partyToCandidates = new Map();
            this.candidates.forEach(c =>{
                let party = c.party;
                if(partyToCandidates.has(party)) {
                    partyToCandidates.get(party).push(c)
                } else {
                    partyToCandidates.set(party, [c])
                }
            })
            return partyToCandidates;
         }
    },
    computed: {
        getTotal() {
            if(this.candidates.length == 0){
                return 0
            }
            return this.candidates.map(a=>a.votes).reduce((a,b) => a+b);
        },
        getChartData() {
            const names = this.candidates.map(candidate => candidate.name)
            const votes = this.candidates.map(candidate => candidate.votes)
            return {
                labels: names,
                datasets: [
                    {
                    label: "Candidates votes",
                    backgroundColor: this.generateRandomColor(),
                    data: votes
                    }
                ]
            }
        },
        getPartyToCandidates() {
            return this.getPartyToCandidatesMap()
         },
         getPartyChartData(){
             let parties = this.getPartyToCandidatesMap()
             const votes = Array.from(parties.values()).map(a=>a.map(c=>c.votes).reduce((a,b)=>a+b))
             return {
                labels: Array.from(parties.keys()),
                datasets: [
                    {
                    label: "Parties votes",
                    backgroundColor: this.generateRandomColor(),
                    data: votes
                    }
                ]
            }
         }
    },
    mounted(){
        axios.get("/rest/get-all-candidates")
            .then(response => response.data)
            .then(data => data.sort((a,b)=>(a.party>b.party)?1:-1))
            .then(data => this.candidates = data)
            .then(() => this.$emit("candidatesUpdate", this.candidates))
            .catch(error => console.log(error))
    }
}
</script>

<style scoped>
    .party {
        width:100%;   
    }
    .voteList{
        align-self: baseline;
    }
    h2 {
        font-size: 1.3rem;
        text-decoration: underline;
    }
    section {
        margin: auto;
        width: 75vw;
        display: flex;
        flex-direction: row;
        justify-content: space-evenly;
        align-items: center;

    }
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
    ul{
        font-size: 1.1rem;
        padding: 0;
        width: 25vw;
    }
    .hidden {
        visibility: hidden;
        opacity: 0;
        transition: visibility 0s 2s, opacity 2s linear;
    }
</style>