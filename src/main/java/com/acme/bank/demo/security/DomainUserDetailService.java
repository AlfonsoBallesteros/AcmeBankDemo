package com.acme.bank.demo.security;

import com.acme.bank.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.acme.bank.demo.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailService")
public class DomainUserDetailService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailService.class);

    private final UserRepository userRepository;

    public DomainUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        String lowercaselogin = login.toLowerCase();
        return userRepository.findOneWithAuthoritiesByLogin(lowercaselogin)
                .map(user -> createSpringSecurityUser(lowercaselogin, user))
                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaselogin + " was not found in the database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaselogin, User user) {
        List<GrantedAuthority> grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), grantedAuthorities);
    }


}
