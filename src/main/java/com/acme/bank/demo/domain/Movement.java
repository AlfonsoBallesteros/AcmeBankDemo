package com.acme.bank.demo.domain;

import com.acme.bank.demo.domain.enumeration.MovementType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "movement")
public class Movement extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 20)
    private String transactionNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    @Column(nullable = false)
    private BigDecimal motionValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movement)) return false;
        return id != null && id.equals(((Movement) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
