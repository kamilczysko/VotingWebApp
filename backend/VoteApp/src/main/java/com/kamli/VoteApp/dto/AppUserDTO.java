package com.kamli.VoteApp.dto;

import com.kamli.VoteApp.domain.user.entity.Role;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    String identityNumber;
    String password;
}
