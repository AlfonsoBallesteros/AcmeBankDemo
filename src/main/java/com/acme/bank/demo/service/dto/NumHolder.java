package com.acme.bank.demo.service.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NumHolder {

    private UUID id;

    private Long numHolder;
}
