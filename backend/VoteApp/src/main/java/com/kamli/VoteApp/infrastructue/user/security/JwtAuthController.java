package com.kamli.VoteApp.infrastructue.user.security;

import com.kamli.VoteApp.dto.AuthRequestDTO;
import com.kamli.VoteApp.infrastructue.ActualUser;
import com.kamli.VoteApp.infrastructue.user.service.JwtUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthController {

    @Autowired
    private JwtUserDetailService userDetailService;
    @Autowired
    private JwtTokenUtil tokenUtil;
    @Autowired
    private AuthenticationManager authenticateManager;

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody AuthRequestDTO jwtRequest) throws Exception {
        auth(jwtRequest.getUsername(), jwtRequest.getPassword());
        ActualUser userDetails = (ActualUser) userDetailService.loadUserByUsername(jwtRequest.getUsername());
        String token = tokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void auth(String login, String password) throws Exception {
        try {
            authenticateManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
