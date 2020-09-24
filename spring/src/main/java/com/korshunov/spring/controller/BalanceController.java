package com.korshunov.spring.controller;

import com.korshunov.spring.entity.Balance;
import com.korshunov.spring.exception.BalanceNotFoundException;
import com.korshunov.spring.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/balances")
@CrossOrigin
public class BalanceController {
    private BalanceService balanceService;

    @GetMapping("/all")
    ResponseEntity<List<Balance>> getAllBalances() {
        List<Balance> balanceList = balanceService.listBalance();
        return new ResponseEntity<>(balanceList, HttpStatus.OK);
    }

    @GetMapping("/balance/{id}")
    ResponseEntity<Balance> getBalance(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(balanceService.findBalance(id), HttpStatus.OK);
        } catch (BalanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Balance not found");
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Balance addBalance(@RequestBody Balance newBalance) {
        return balanceService.create(newBalance);
    }

    @PutMapping(value = "/balance/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Balance> updateBalance(@PathVariable Long id, @Valid @RequestBody Balance balance) {
        if (!id.equals(balance.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Balance not found");
        }
        return new ResponseEntity<>(balanceService.update(balance), HttpStatus.OK);
    }

    @DeleteMapping("/balance/{id}")
    public ResponseEntity<Balance> deleteBalance(@PathVariable Long id) {
        try {
            balanceService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BalanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Balance not found");
        }
    }

    @Autowired
    public void setBalanceService(BalanceService balanceService) {
        this.balanceService = balanceService;
    }
}
