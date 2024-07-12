package com.acme.bank.demo.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.acme.bank.demo.domain.User;
import com.acme.bank.demo.security.TokenProvider;
import com.acme.bank.demo.service.UserService;
import com.acme.bank.demo.service.dto.LoginDTO;
import com.acme.bank.demo.service.dto.RegisterUserDTO;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    public AccountController(UserService userService, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@RequestBody LoginDTO login){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        return new ResponseEntity<>(new JWTToken(jwt), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerAccount(@RequestBody RegisterUserDTO userDTO){
        if(isPasswordLength(userDTO.getPassword())){
            throw new RuntimeException(HttpStatus.BAD_REQUEST.getReasonPhrase() + " Incorrect password");
        }
        User user = userService.registerUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/account")
    @PreAuthorize("isAuthenticated()")
    public User getAccount(){
        return userService
                .getUserWithAuthorities()
                .orElseThrow(() -> new RuntimeException("User could no be found"));
    }

    private static boolean isPasswordLength(String password){
        return (StringUtils.isEmpty(password)
                || password.length() < RegisterUserDTO.PASSWORD_MIN_LENGTH
                || password.length() > RegisterUserDTO.PASSWORD_MAX_LENGTH);
    }

    static class JWTToken{

        private String token;

        public JWTToken(String token) {
            this.token = token;
        }

        @JsonProperty("token")
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
