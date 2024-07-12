package com.acme.bank.demo.service;

import com.acme.bank.demo.domain.*;
import com.acme.bank.demo.repository.AccountRepository;
import com.acme.bank.demo.service.dto.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService {
    private final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    public List<DebtFranchise> getAllDebtByFranchise(){
        List<DebtFranchise> debtFranchises = new ArrayList<>();
        accountRepository.findAllDebtByFranchise().forEach(debt -> {
            DebtFranchise debtFranchise = new DebtFranchise();
            debtFranchise.setFranchise(debt.getFranchise());
            debtFranchise.setTotalDebt(debt.getTotalDebt());
            debtFranchises.add(debtFranchise);
        });
        return debtFranchises;
    }

    public RedeemBalance getClientBalanceInChange(){
        getClientBalanceInChange getClientBalanceInChange = accountRepository.findClientBalanceInChange();
        RedeemBalance redeemBalance = new RedeemBalance();
        redeemBalance.setId(getClientBalanceInChange.getId());
        redeemBalance.setRedeemBalance(getClientBalanceInChange.getRedeemBalance());
        return redeemBalance;
    }

    public TotalWithdrawals getTotalWithdrawals(LocalDate init, LocalDate end){
        getTotalWithdrawals getTotalWithdrawals = accountRepository.findTotalWithdrawalsClient(init, end);
        return TotalWithdrawals.builder().id(getTotalWithdrawals.geId()).totalWithdrawals(getTotalWithdrawals.getTotalWithdrawals()).build();
    }

    public NumHolder getNumHolders(){
        getNumHolders numHolders = accountRepository.findCountNumHolder();
        return NumHolder.builder().id(numHolders.getId()).numHolder(numHolders.getNumHolder()).build();
    }

    public TotalAccountBalance getTotalAccountBalance(UUID userId){
        getTotalAccountBalance getTotalAccountBalance = accountRepository.findTotalAccountBalanceClient(userId);
        return TotalAccountBalance.builder().id(getTotalAccountBalance.getId()).totalAccountBalance(getTotalAccountBalance.getTotalAccountBalance()).build();
    }

    public Long getCountForeignClient(){
        return accountRepository.findCountAccountForeignClient().getForeignClient();
    }

    public List<TotalAccountDebt> getTotalDebtStockHolderClient(){
        List<getTotalDebtStockHolderClient> getTotalDebtStockHolderClient = accountRepository.findTotalDebStockHolderClient();
        List<TotalAccountDebt> totalAccountDebt = new ArrayList<>();
        getTotalDebtStockHolderClient.forEach(debt -> {
            totalAccountDebt.add(TotalAccountDebt.builder().id(debt.getId()).totalAccountDebt(debt.getTotalAccountDebt()).build());
        });
        return totalAccountDebt;
    }
}
