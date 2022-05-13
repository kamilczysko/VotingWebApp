# VotingWebApp
vue + spring boot
This is simple voting app. This project was made to try tie together spring boot on backend and vue on frontend. 
There is used Spring Security with jwt tokens. Frontend is very simple and it's made with vue.js..
Backend app uses built-in database H2, so data won't be persisted after restart.

To run app:
  - run command in backend/VoteApp **mvn clean install** and then **java -jar /target/VoteApp/VoteApp-0.0.1-SNAPSHOT.jar** backend will run on port 8080
  - run frontend - goto frontend/vote-app-view then run **npn run serve**. You can also build forntend and put it to spring app.
