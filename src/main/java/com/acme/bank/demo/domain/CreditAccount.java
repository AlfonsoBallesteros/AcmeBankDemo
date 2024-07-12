package com.acme.bank.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Entity
@DiscriminatorValue("CREDIT")
public class CreditAccount extends Account {

    @Column(nullable = false, length = 20)
    private String cardNumber;

    @Column(nullable = false, length = 50)
    private String franchise;
}
