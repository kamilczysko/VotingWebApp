<template>
    <main>
        <section>
            <div class="voteList">
                <h2>Vote:</h2>
                <ul>
                    <li v-for="c in candidates" v-bind:key="c.id"><button v-on:click="vote(c.id)">{{c.name}}</button> - {{c.votes}}</li>
                </ul>
            </div>
            <Bar :chart-data="getChartData" :chart-options="chartOptions"/>
        </section>
        <p>Total votes: {{getTotal}}</p>
    </main>
</template>

<script>
import { Bar } from 'vue-chartjs'


import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
    name: "candidate-list",
    components: {
        Bar
    },
    data: () => {
        return {
            candidates: [{"id": 1, "name": "adam", "votes": 1},{"id": 2, "name": "pszemek", "votes": 5}, {"id": 3, "name": "franciszek", "votes": 19}],
            chartOptions: {
                responsive: true,
                maintainAspectRatio: false
            }
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
             console.log("voted on: "+id)
             this.candidates.filter(candidate => candidate.id === id).map(candidate => candidate.votes = candidate.votes+1)
         }
    },
    computed: {
        getTotal() {
            return this.candidates.map(a=>a.votes).reduce((a,b) => a+b);
        },
        getChartData() {
            const names = this.candidates.map(candidate => candidate.name)
            const votes = this.candidates.map(candidate => candidate.votes)
            return {
                labels: names,
                datasets: [
                    {
                    label: "Votes",
                    backgroundColor: this.generateRandomColor(),
                    data: votes
                    }
                ]
            }
        
        }
    }
}
</script>

<style scoped>
    h2 {
        font-size: 1.3rem;
    }
    section {
        margin: auto;
        width: 65vw;
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
    li {
        margin-top: 4px;
        list-style: none;
    }
    .voteList{

    }
</style>