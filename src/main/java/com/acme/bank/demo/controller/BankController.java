package com.acme.bank.demo.controller;

import com.acme.bank.demo.domain.*;
import com.acme.bank.demo.service.AccountService;
import com.acme.bank.demo.service.dto.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BankController {

    private final Logger log = LoggerFactory.getLogger(BankController.class);

    private final AccountService accountService;

    @GetMapping("/total-debt-franchise")
    public ResponseEntity<List<DebtFranchise>> totalDebtFranchise(){
        return new ResponseEntity<>(accountService.getAllDebtByFranchise(), HttpStatus.OK);
    }

    @GetMapping("/client-balance-exchange")
    public ResponseEntity<RedeemBalance> clientBalanceInChange(){
        return new ResponseEntity<>(accountService.getClientBalanceInChange(), HttpStatus.OK);
    }

    @GetMapping("/total-withdrawals/{init}/{end}")
    public ResponseEntity<TotalWithdrawals> totalWithdrawals(@PathVariable LocalDate init, @PathVariable LocalDate end){
        return new ResponseEntity<>(accountService.getTotalWithdrawals(init, end), HttpStatus.OK);
    }

    @GetMapping("/num-holders")
    public ResponseEntity<NumHolder> numHolders(){
        return new ResponseEntity<>(accountService.getNumHolders(), HttpStatus.OK);
    }

    @GetMapping("/total-account-balance/{id}")
    public ResponseEntity<TotalAccountBalance> totalAccountBalance(@PathVariable UUID id){
        return new ResponseEntity<>(accountService.getTotalAccountBalance(id), HttpStatus.OK);
    }

    @GetMapping("/count-foreign-client")
    public ResponseEntity<Long> countForeignClient(){
        return new ResponseEntity<>(accountService.getCountForeignClient(), HttpStatus.OK);
    }


    @GetMapping("/total-debt-stockholder-client")
    public ResponseEntity<List<TotalAccountDebt>> totalDebtStockHolderClient(){
        return new ResponseEntity<>(accountService.getTotalDebtStockHolderClient(), HttpStatus.OK);
    }

}
