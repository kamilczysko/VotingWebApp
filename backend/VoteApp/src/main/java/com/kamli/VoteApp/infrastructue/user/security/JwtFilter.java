package com.kamli.VoteApp.infrastructue.user.security;

import com.kamli.VoteApp.infrastructue.user.service.JwtUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailService userService;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Value("${jwt.secret}")
    private String secret = "abcdef";

    private UserDetails getAuthenticationByToken(String header) {

        final String BEARER = "Bearer ";
        final String EMPTY_STRING = "";

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(header.replace(BEARER, EMPTY_STRING));
        String username = claimsJws.getBody().getSubject();

        UserDetails userDetails = userService.loadUserByUsername(username);

        return userDetails;
    }

    private String getHeaderFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private Set<SimpleGrantedAuthority> getAllAuthorities(String role) {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    List<String> whiteList = Arrays.asList("/register", "/auth");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (whiteList.contains(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
        String header = getHeaderFromRequest(request);
        UserDetails authResult = getAuthenticationByToken(header);
        String token = header.substring(7);
        if(tokenUtil.validateToken(token, authResult)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authResult, null, authResult.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
