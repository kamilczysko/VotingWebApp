package com.kamli.VoteApp.domain.user;

import com.kamli.VoteApp.domain.configuration.UsersConfiguration;
import com.kamli.VoteApp.domain.user.entity.AppUser;
import com.kamli.VoteApp.domain.user.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    ApplicationContext context = new AnnotationConfigApplicationContext(UsersConfiguration.class);

    @Override
    public UserDetails loadUserByUsername(String identityNumber) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByIdentityNumber(identityNumber);
        System.out.println("Login user: "+appUser);
        if (appUser == null) {
            throw new IllegalStateException("User does not exists");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + appUser.getRole().name()));
        return new User(appUser.getIdentityNumber(), appUser.getPasswordHash(), true, true, true, true, grantedAuthorities);
    }

    public void registerNewUser(AppUser newUser) {
        System.out.println("Register new user: "+newUser);
        Set<String> bannedUsers = context.getBean("bannedUsers", HashSet.class);
        if (bannedUsers.contains(newUser.getIdentityNumber())) {
            userRepository.save(mapDisallowedUser(newUser));
        } else {
            userRepository.save(newUser);
        }
    }

    private AppUser mapDisallowedUser(AppUser newUser) {
        return AppUser.builder().identityNumber(newUser.getIdentityNumber())
                .passwordHash(newUser.getPasswordHash())
                .banned(true)
                .role(Role.DISALLOWED)
                .build();
    }
}
