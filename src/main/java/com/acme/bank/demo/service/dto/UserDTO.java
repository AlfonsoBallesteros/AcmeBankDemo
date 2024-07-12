package com.acme.bank.demo.service.dto;

import com.acme.bank.demo.domain.Authority;
import com.acme.bank.demo.domain.User;
import com.acme.bank.demo.domain.enumeration.TypeId;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO extends AbstractAuditingDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;

    private String login;

    private String password;

    private String name;

    private String lastName;

    private TypeId typeId;

    private String documentId;

    private String phoneNumber;

    private String department;

    private String city;

    private Set<String> authorities;

    private boolean activated = true;


    public UserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.lastName = user.getLastName();
        this.typeId = user.getTypeId();
        this.documentId = user.getDocumentId();
        this.department = user.getDepartment();
        this.city = user.getCity();
        this.setCreatedDate(user.getCreatedDate());
        this.setLastModifiedDate(user.getLastModifiedDate());
        this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
    }

}
