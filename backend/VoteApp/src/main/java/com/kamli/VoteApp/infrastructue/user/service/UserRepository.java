package com.kamli.VoteApp.infrastructue.user.service;

import com.kamli.VoteApp.infrastructue.user.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByIdentityNumber(String identityNumber);
}
