package com.kamli.VoteApp.domain.user;

import com.kamli.VoteApp.domain.configuration.BannedUsersConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    ApplicationContext context = new AnnotationConfigApplicationContext(BannedUsersConfiguration.class);

    @GetMapping("/disallowed")
    public List<String> getDisallowedUsers() {
        System.out.println("get banned users endpoint");
        return context.getBean("bannedUsers", List.class);
    }
}
