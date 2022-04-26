package com.kamli.VoteApp.domain.user;

import com.kamli.VoteApp.domain.configuration.UsersConfiguration;
import com.kamli.VoteApp.domain.user.entity.AppUser;
import com.kamli.VoteApp.domain.user.entity.Role;
import com.kamli.VoteApp.dto.AppUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private ApplicationContext context = new AnnotationConfigApplicationContext(UsersConfiguration.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailService userService;

    @GetMapping("/disallowed")
    public List<String> getDisallowedUsers() {
        return context.getBean("bannedUsers", List.class);
    }

    @GetMapping("/me")
    public String getUserInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName() + " - "+auth.getPrincipal().toString() +" -- "+auth
                .getDetails();
    }

    @PostMapping("/register")
    public ResponseEntity registerNewUser(@RequestBody AppUserDTO newUser) {
        AppUser user = AppUser.builder()
                .identityNumber(newUser.getIdentityNumber())
                .passwordHash(passwordEncoder.encode(newUser.getPassword()))
                .role(Role.ALLOWED)
                .build();
        userService.registerNewUser(user);
        return ResponseEntity.ok().build();
    }
}
