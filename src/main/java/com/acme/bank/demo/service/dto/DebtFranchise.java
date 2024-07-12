package com.acme.bank.demo.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DebtFranchise {

    private String franchise;

    private BigDecimal totalDebt;
}
