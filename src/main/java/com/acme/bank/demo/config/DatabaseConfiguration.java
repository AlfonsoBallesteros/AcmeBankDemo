package com.acme.bank.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({"com.acme.bank.demo.repository"})
@EnableTransactionManagement
public class DatabaseConfiguration {
}
