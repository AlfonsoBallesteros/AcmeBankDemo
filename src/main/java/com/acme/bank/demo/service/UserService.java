package com.acme.bank.demo.service;

import com.acme.bank.demo.domain.LegalUser;
import com.acme.bank.demo.repository.AuthorityRepository;
import com.acme.bank.demo.repository.UserRepository;
import com.acme.bank.demo.security.AuthoritiesConstants;
import com.acme.bank.demo.security.SecurityUtils;
import com.acme.bank.demo.domain.Authority;
import com.acme.bank.demo.domain.User;
import com.acme.bank.demo.service.dto.RegisterUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    private static final SecureRandom SECURE_RANDOM;

    static{
        SECURE_RANDOM = new SecureRandom();
        SECURE_RANDOM.nextBytes(new byte[64]);
    }

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterUserDTO userDTO){
        userRepository.findOneByLogin(userDTO.getLogin().toLowerCase())
                .ifPresent(user -> {
                    throw new RuntimeException("Login name already used!");
                });
        User user = new User();
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setLogin(userDTO.getLogin());
        user.setName(userDTO.getName()); //razon social
        if(!userDTO.getLastName().isEmpty()){
            user.setLastName(userDTO.getLastName());
        }
        user.setTypeId(userDTO.getTypeId());
        user.setDocumentId(userDTO.getDocumentId());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(encryptedPassword);

        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        user.setAuthorities(authorities);
        if(userDTO.getTypeCompany() != null){
            LegalUser legalUser = new LegalUser(user);
            legalUser.setTypeCompany(userDTO.getTypeCompany());
            //legalUser.setManager();
            userRepository.save(legalUser);
        }else {
            userRepository.save(user);
        }
        log.debug("Created information for user: {}", user.toString());
        return user;
    }

    @Transactional
    public Optional<User> getUserWithAuthorities(){
        return SecurityUtils.getCurrentLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }


}
