package com.acme.bank.demo.domain;

import com.acme.bank.demo.domain.enumeration.TypeId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DiscriminatorFormula;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "usuario", schema = "public")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "userType", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorFormula("""
    case when userType is null then 'USER'
""")
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 20)
    private String login;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 50)
    private String lastName;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TypeId typeId;

    @Column(nullable = false, length = 20)
    private String documentId;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(length = 20)
    private String department;

    @Column(length = 20)
    private String city;

    private boolean activated = true;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}


