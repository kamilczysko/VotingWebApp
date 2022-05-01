package com.kamli.VoteApp.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {

    private String username;
    private String password;


    public AuthRequestDTO(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
}
