<template>
  <main class="main">
    <Message
      v-bind:message="testMessage"
      v-bind:type="type"
      v-if="isMessageVisible"
      v-on:closePopup="closePopup"
    />
    <nav>
      <h1 class="header">Vote App</h1>
      <div class="menu">
        <router-link to="/user-panel" class="menu-item" v-if="!isLogged">
          <svg
            width="24"
            height="24"
            viewBox="0 0 24 24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              fill-rule="evenodd"
              clip-rule="evenodd"
              d="M16 7C16 9.20914 14.2091 11 12 11C9.79086 11 8 9.20914 8 7C8 4.79086 9.79086 3 12 3C14.2091 3 16 4.79086 16 7ZM14 7C14 8.10457 13.1046 9 12 9C10.8954 9 10 8.10457 10 7C10 5.89543 10.8954 5 12 5C13.1046 5 14 5.89543 14 7Z"
              fill="currentColor"
            />
            <path
              d="M16 15C16 14.4477 15.5523 14 15 14H9C8.44772 14 8 14.4477 8 15V21H6V15C6 13.3431 7.34315 12 9 12H15C16.6569 12 18 13.3431 18 15V21H16V15Z"
              fill="currentColor"
            />
          </svg>
        </router-link>
        <router-link to="/" class="menu-item">
          <svg
            width="24"
            height="24"
            viewBox="0 0 24 24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path d="M15.9644 4.63379H3.96442V6.63379H15.9644V4.63379Z" fill="currentColor" />
            <path d="M15.9644 8.63379H3.96442V10.6338H15.9644V8.63379Z" fill="currentColor" />
            <path d="M3.96442 12.6338H11.9644V14.6338H3.96442V12.6338Z" fill="currentColor" />
            <path
              d="M12.9645 13.7093L14.3787 12.295L16.5 14.4163L18.6213 12.2951L20.0355 13.7093L17.9142 15.8305L20.0356 17.9519L18.6214 19.3661L16.5 17.2447L14.3786 19.3661L12.9644 17.9519L15.0858 15.8305L12.9645 13.7093Z"
              fill="currentColor"
            />
          </svg>
        </router-link>
        <a v-on:click="print" class="menu-item" v-if="isListActive">
          <svg
            width="24"
            height="24"
            viewBox="0 0 24 24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              fill-rule="evenodd"
              clip-rule="evenodd"
              d="M8 4H16V6H8V4ZM18 6H22V18H18V22H6V18H2V6H6V2H18V6ZM20 16H18V14H6V16H4V8H20V16ZM8 16H16V20H8V16ZM8 10H6V12H8V10Z"
              fill="currentColor"
            />
          </svg>
        </a>
      </div>
      <div class="login-info">
        <p class="login-info__text" v-if="isLogged">
          You're logged in as
          <b>{{this.$store.state.username}}</b>
        </p>
        <button v-if="isLogged" v-on:click="logout">Logout</button>
      </div>
    </nav>
    <section>
      <router-view v-on:candidatesUpdate="candidatesUpdate"></router-view>
    </section>
    <div class="printMe" id="printMe">
      <div class="print-container">
        <h2>Votes by parties</h2>
        <ul class="print-container">
          <li v-for="ptv in partyToVotes" v-bind:key="ptv.name">{{ptv.name}} - {{ptv.votes}}</li>
        </ul>
      </div>
      <div class="print-container">
        <h2>Votes by candidates</h2>
        <ul class="print-container">
          <li v-for="c in candidates" v-bind:key="c.name">{{c.name}} ({{c.party}}) - {{c.votes}}</li>
        </ul>
      </div>
    </div>
  </main>
</template>

<script>
import Message from "./components/messages/Message.vue";
export default {
  name: "App",
  data: () => {
    return {
      isLoggedIn: false,
      isMenuVisible: false,
      type: "error",
      isMessageVisible: false,
      stylesHtml: "",
      prtHtml: "",
      candidates: [],
      partyToVotes: []
    };
  },
  components: {
    Message
  },
  methods: {
    getPartyToCandidatesMap() {
      const partyToCandidates = new Map();
      this.candidates.forEach(c => {
        let party = c.party;
        if (partyToCandidates.has(party)) {
          partyToCandidates.get(party).push(c);
        } else {
          partyToCandidates.set(party, [c]);
        }
      });
      return partyToCandidates;
    },
    candidatesUpdate(candidatesList) {
      this.candidates = candidatesList;
      let parties = this.getPartyToCandidatesMap()
      const partyMap = []
      parties.forEach((values, key) => { 
        partyMap.push({name: key, votes: values.map(a => a.votes).reduce((a,b)=>a+b)})
      })
      this.partyToVotes = partyMap;
    },
    logout() {
      this.$store.commit("clear");
    },
    toggleMenu() {
      this.isMenuVisible = !this.isMenuVisible;
    },
    print() {
      const prtHtml = document.getElementById("printMe").innerHTML;
      let stylesHtml = "";
      for (const node of [
        ...document.querySelectorAll('link[rel="stylesheet"], style')
      ]) {
        stylesHtml += node.outerHTML;
      }
      const WinPrint = window.open(
        "",
        "",
        "left=0,top=0,width=800,height=900,toolbar=0,scrollbars=0,status=0"
      );

      WinPrint.document.write(`<!DOCTYPE html>
        <html>
          <head>
            ${stylesHtml}
          </head>
          <body>
            ${prtHtml}
          </body>
        </html>`);

      WinPrint.document.close();
      WinPrint.focus();
      WinPrint.print();
      WinPrint.close();
    },
    closePopup() {
      this.isMessageVisible = false;
    },
    setMessage(msg, tp) {
      this.type = tp;
      this.testMessage = msg;
      this.isMessageVisible = true;
    }
  },
  computed: {
    isLogged() {
      return this.$store.getters.isLoggedIn;
    },
    isListActive() {
      return this.$route.name === "vote-list";
    }
  }
};
</script>

<style scoped>
.printMe {
  display: none;
  position: absolute;
  width: 80vh;
}
.print-container {
  width: 80vh;
}
.login-info__text {
  text-align: center;
}
.login-info {
  display: flex;
  margin-left: 50px;
  justify-content: center;
  flex-direction: column;
  align-items: center;
}
.menu-item {
  height: fit-content;
}
.menu {
  display: flex;
  justify-content: space-evenly;
  width: 100%;
}
button {
  border: none;
  background: none;
}
nav {
  display: grid;
  grid-template-columns: 2fr 2fr 1fr 1fr;
  width: 100%;
  height: 10vh;
}
nav a {
  grid-column: 4;
  display: flex;
  justify-content: center;
  align-self: center;
  text-decoration: none;
  color: black;
  height: 10vh;
}
nav a:hover {
  font-weight: bolder;
  position: relative;
  bottom: 2px;
  left: 2px;
}
nav a:active {
  position: relative;
  top: 0.1px;
  font-weight: bolder;
}
nav h1 {
  grid-column: 2;
  display: flex;
  align-self: center;
  justify-content: center;
}
main {
  display: flex;
  height: 70vh;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
}
.hidden {
  display: none;
}
section {
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
}
</style>
