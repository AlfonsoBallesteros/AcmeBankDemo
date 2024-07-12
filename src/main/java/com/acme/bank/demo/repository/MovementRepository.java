package com.acme.bank.demo.repository;

import com.acme.bank.demo.domain.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovementRepository extends JpaRepository<Movement, UUID> {
}
