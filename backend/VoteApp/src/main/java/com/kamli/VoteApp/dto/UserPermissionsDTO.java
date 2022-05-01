package com.kamli.VoteApp.dto;

public class UserPermissionsDTO {

    public boolean hasVoted;
    public boolean isBanned;

    public UserPermissionsDTO(boolean hasVoted, boolean isBanned) {
        this.hasVoted = hasVoted;
        this.isBanned = isBanned;
    }

    public boolean isHasVoted() {
        return hasVoted;
    }

    public boolean isBanned() {
        return isBanned;
    }
}
