package com.acme.bank.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Entity
@DiscriminatorValue("SAVING")
public class SavingAccount extends Account{

    @Column(nullable = false, length = 20)
    private String accountNumber;

    @Column(nullable = false, length = 20)
    private BigDecimal balanceInExchange;

}
