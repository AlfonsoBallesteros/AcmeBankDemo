package com.acme.bank.demo.domain;

import com.acme.bank.demo.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "accountType", discriminatorType = DiscriminatorType.STRING)
public class Account extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 20)
    private BigDecimal totalBalance;

    @Column(nullable = false, length = 50)
    private BigDecimal availableBalance;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToMany
    @JoinTable(
            name = "user_account",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "account_id", referencedColumnName = "id") }
    )
    @BatchSize(size = 20)
    private Set<User> headlines = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        return id != null && id.equals(((Account) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
