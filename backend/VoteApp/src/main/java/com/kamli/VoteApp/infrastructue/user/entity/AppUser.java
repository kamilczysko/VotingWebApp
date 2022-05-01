package com.kamli.VoteApp.infrastructue.user.entity;

import com.kamli.VoteApp.infrastructue.user.entity.Role;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "User")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String identityNumber;
    String passwordHash;
    boolean hasVoted = false;
    boolean banned = false;
    Role role = Role.ALLOWED;
}
