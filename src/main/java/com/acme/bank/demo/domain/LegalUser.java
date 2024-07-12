package com.acme.bank.demo.domain;

import com.acme.bank.demo.domain.enumeration.TypeCompany;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@DiscriminatorValue("LEGAL")
@AllArgsConstructor
@NoArgsConstructor
public class LegalUser extends User {

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TypeCompany typeCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToMany
    @JoinTable(
            name = "user_stockholder",
            joinColumns = { @JoinColumn(name = "stockholder_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "legal_user_id", referencedColumnName = "id") }
    )
    @BatchSize(size = 20)
    private Set<User> stockholder = new HashSet<>();

    public LegalUser(User user) {
        super(user.getId(), user.getName(), user.getLogin(), user.getPassword(), user.getPhoneNumber(), user.getTypeId(), user.getDocumentId(), user.getPhoneNumber(), user.getDepartment(), user.getCity(), user.isActivated(), user.getAuthorities());
    }
}
