package com.kamli.VoteApp.domain.user.entity;

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
