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
public class TotalWithdrawals {
    private UUID id;

    private BigDecimal totalWithdrawals;
}
