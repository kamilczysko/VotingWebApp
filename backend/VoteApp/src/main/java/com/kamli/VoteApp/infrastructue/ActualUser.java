package com.kamli.VoteApp.infrastructue;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


@ToString
public class ActualUser extends User {

    private boolean voted;
    private boolean isBanned;
    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    private ActualUser(Builder builder) {
        super(builder.username, builder.password, true, true, true, true, builder.authorities);
        voted = builder.voted;
        isBanned = builder.isBanned;
        id = builder.id;
        username = builder.username;
        password = builder.password;
        authorities = builder.authorities;
    }

    public boolean isVoted() {
        return voted;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public Long getId() {
        return id;
    }

    public static Builder userBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private boolean voted;
        private boolean isBanned;
        private Long id;
        private String username;
        private String password;
        private Collection<? extends GrantedAuthority> authorities;

        private Builder() {
        }

        public Builder setVoted(boolean val) {
            voted = val;
            return this;
        }

        public Builder setIsBanned(boolean val) {
            isBanned = val;
            return this;
        }

        public Builder setId(Long val) {
            id = val;
            return this;
        }

        public Builder setUsername(String val) {
            username = val;
            return this;
        }

        public Builder setPassword(String val) {
            password = val;
            return this;
        }

        public Builder setAuthorities(Collection<? extends GrantedAuthority> val) {
            authorities = val;
            return this;
        }

        public ActualUser build() {
            return new ActualUser(this);
        }
    }
}
