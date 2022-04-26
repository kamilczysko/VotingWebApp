package com.kamli.VoteApp.domain.user;

import com.kamli.VoteApp.domain.user.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByIdentityNumber(String identityNumber);
}
