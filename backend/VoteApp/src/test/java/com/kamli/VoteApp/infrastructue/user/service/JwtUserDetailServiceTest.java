package com.kamli.VoteApp.infrastructue.user.service;

import com.kamli.VoteApp.infrastructue.user.entity.AppUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class JwtUserDetailServiceTest {

    @Test
    void registerNewUserTooLongIDNumber() {
        JwtUserDetailService service = new JwtUserDetailService();
        AppUser user = AppUser.builder().identityNumber("123456748902").build();
        assertThrows(IllegalStateException.class, () -> service.registerNewUser(user));
    }


    @Test
    void registerNewUserTooShortIDNumber() {
        JwtUserDetailService service = new JwtUserDetailService();
        AppUser user = AppUser.builder().identityNumber("234").build();
        assertThrows(IllegalStateException.class, () -> service.registerNewUser(user));
    }

    @Test
    void registerNewUserTooTooEarlyDate() {
        JwtUserDetailService service = new JwtUserDetailService();
        AppUser user = AppUser.builder().identityNumber("01234234234").build();
        assertThrows(IllegalStateException.class, () -> service.registerNewUser(user));
    }

    @Test
    void registerNewUserIsOk() {
        JwtUserDetailService service = new JwtUserDetailService();
        service.context = Mockito.mock(ApplicationContext.class);
        when(service.context.getBean(any(), eq(HashSet.class))).thenReturn(new HashSet());
        service.userRepository = Mockito.mock(UserRepository.class);
        AppUser user = AppUser.builder().identityNumber("49345345345").build();
        assertAll(() -> service.registerNewUser(user));
    }

}