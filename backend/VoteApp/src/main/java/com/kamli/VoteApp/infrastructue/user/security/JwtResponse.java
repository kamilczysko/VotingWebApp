package com.kamli.VoteApp.infrastructue.user.security;

import com.kamli.VoteApp.infrastructue.ActualUser;

public class JwtResponse{
    private final String jwttoken;
    private final String username;
    private final boolean hasVoted;
    private final boolean isBanned;

    public JwtResponse(String jwttoken, String username, boolean hasVoted, boolean isBanned) {
        this.jwttoken = jwttoken;
        this.username = username;
        this.hasVoted = hasVoted;
        this.isBanned = isBanned;
    }

    public JwtResponse(String jwttoken, ActualUser userDetails) {
        this.jwttoken = jwttoken;
        this.username = userDetails.getUsername();
        this.hasVoted = userDetails.isVoted();
        this.isBanned = userDetails.isBanned();
    }

    public String getToken() {
        return this.jwttoken;
    }

    public String getUsername() {
        return username;
    }

    public boolean isHasVoted() {
        return hasVoted;
    }

    public boolean isBanned() {
        return isBanned;
    }
}
