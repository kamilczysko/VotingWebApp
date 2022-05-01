package com.kamli.VoteApp.infrastructue.config;

import com.kamli.VoteApp.infrastructue.user.entity.BannedUser;
import com.kamli.VoteApp.infrastructue.user.entity.BannedUsers;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class UsersConfiguration {

    @Bean(name = "bannedUsers")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Set<String> getBannedUsers() {
        BannedUsers bannedUsers = new RestTemplate()
                .getForObject("http://webtask.future-processing.com:8069/blocked", BannedUsers.class);
        return bannedUsers.getPerson().stream()
                .map(BannedUser::getPesel)
                .collect(Collectors.toSet());
    }
}
