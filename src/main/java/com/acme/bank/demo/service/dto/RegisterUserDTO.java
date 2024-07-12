package com.acme.bank.demo.service.dto;

import com.acme.bank.demo.domain.enumeration.TypeCompany;
import com.acme.bank.demo.domain.enumeration.TypeId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class RegisterUserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    private String login;

    private String password;

    private String name;

    private String lastName;

    private TypeId typeId;

    private String documentId;

    private String phoneNumber;

    private String department;

    private String city;

    private TypeCompany typeCompany;

    private UUID manager;


}
