package com.acme.bank.demo.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RedeemBalance {

    private UUID id;

    private BigDecimal redeemBalance;


}
