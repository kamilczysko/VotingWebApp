package com.kamli.VoteApp.domain.user;

import com.kamli.VoteApp.domain.configuration.UsersConfiguration;
import com.kamli.VoteApp.domain.user.entity.AppUser;
import com.kamli.VoteApp.domain.user.entity.Role;
import com.kamli.VoteApp.infrastructue.ActualUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService {

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
        return ActualUser.userBuilder()
                .setUsername(appUser.getIdentityNumber())
                .setPassword(appUser.getPasswordHash())
                .setId(appUser.getId())
                .setAuthorities(grantedAuthorities)
                .setVoted(appUser.isHasVoted())
                .setIsBanned(appUser.isBanned())
                .build();
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

    private AppUser mapVotedUser(AppUser user) {
        return AppUser.builder()
                .id(user.getId())
                .identityNumber(user.getIdentityNumber())
                .passwordHash(user.getPasswordHash())
                .hasVoted(true)
                .role(user.getRole())
                .build();
    }

    public AppUser getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User should be present"));
    }

    public AppUser userHasVoted(Long userId){
        ActualUser user = (ActualUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser userById = userRepository.findById(userId).orElseThrow();
        if(!user.getId().equals(userById.getId())) {
            throw  new IllegalStateException("Other user cannot perform this operation");
        }
        AppUser userToUpdate = mapVotedUser(userById);
        return userRepository.save(userToUpdate);
    }
}
