package com.acme.bank.demo.service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotalAccountDebt {

    private UUID id;

    private BigDecimal totalAccountDebt;
}
