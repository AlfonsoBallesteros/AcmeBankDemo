package com.acme.bank.demo.repository;

import com.acme.bank.demo.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Query(value = "SELECT franchise, SUM(total_balance - available_balance) AS total_debt\n" +
            "FROM account i\n" +
            "WHERE i.account_type = 'CREDIT' AND i.franchise IN ('VISA', 'MASTERCARD', 'AMERICAN', 'DINERS')\n" +
            "GROUP BY franchise;", nativeQuery = true)
    List<getTotalDebt> findAllDebtByFranchise();

    @Query(value = "SELECT c.id, a.balance_in_exchange AS redeem_balance\n" +
            "FROM account a\n" +
            "JOIN user_account ac ON a.id = ac.account_id\n" +
            "JOIN usuario c ON ac.user_id = c.id\n" +
            "WHERE a.account_type = 'SAVING'\n" +
            "GROUP BY c.id, a.balance_in_exchange\n" +
            "ORDER BY a.balance_in_exchange DESC\n" +
            "LIMIT 1;", nativeQuery = true)
    getClientBalanceInChange findClientBalanceInChange();

    @Query(value = "SELECT c.id, SUM(t.motion_value) AS total_withdrawals\n" +
            "FROM usuario c\n" +
            "JOIN user_account ac ON c.id = ac.user_id\n" +
            "JOIN account a ON ac.account_id = a.id\n" +
            "JOIN movement t ON a.id = t.account_id\n" +
            "WHERE a.account_type = 'SAVING'\n" +
            "AND t.created_date BETWEEN :init AND :end\n" +
            "GROUP BY c.id\n" +
            "ORDER BY SUM(t.motion_value) DESC\n" +
            "LIMIT 1;", nativeQuery = true)
    getTotalWithdrawals findTotalWithdrawalsClient(@Param("init") LocalDate init, @Param("end") LocalDate end);

    @Query(value = "SELECT a.id, COUNT(ah.user_id) AS num_holder\n" +
            "FROM account a\n" +
            "JOIN user_account ah ON a.id = ah.account_id\n" +
            "WHERE a.account_type = 'SAVING'\n" +
            "GROUP BY a.id\n" +
            "ORDER BY num_account_holders DESC\n" +
            "LIMIT 1;", nativeQuery = true)
    getNumHolders findCountNumHolder();

    @Query(value = "SELECT c.id, SUM(a.total_balance) AS total_account_balance\n" +
            "FROM usuario c\n" +
            "JOIN user_account ac ON c.id = ac.user_id\n" +
            "JOIN account a ON ac.account_id = a.id\n" +
            "WHERE a.account_type = 'SAVING'\n" +
            "AND c.id = :userId\n" +
            "GROUP BY c.id;", nativeQuery = true)
    getTotalAccountBalance findTotalAccountBalanceClient(@Param("userId") UUID userId);

    @Query(value = "SELECT COUNT(*) AS foreign_client\n" +
            "FROM account a\n" +
            "JOIN user_account ac ON a.id = ac.account_id\n" +
            "JOIN usuario c ON ac.user_id = c.id\n" +
            "WHERE a.account_type = 'SAVING'\n" +
            "AND a.state = 'ACTIVO'\n" +
            "AND c.type_id = 'CEDULAEXTRANJERIA';", nativeQuery = true)
    getCountForeignClient findCountAccountForeignClient();

    @Query(value = "SELECT c.id, SUM(a.total_balance - a.available_balance) AS total_account_debt\n" +
            "FROM usuario c\n" +
            "JOIN user_stockholder cs ON c.id = cs.stockholder_id\n" +
            "JOIN user_account ac ON c.id = ac.user_id\n" +
            "JOIN account a ON ac.account_id = a.id\n" +
            "AND a.account_type = 'CREDIT'\n" +
            "GROUP BY c.id\n" +
            "HAVING SUM(a.total_balance - a.available_balance) > 100000;", nativeQuery = true)
    List<getTotalDebtStockHolderClient> findTotalDebStockHolderClient();

}
