package com.korshunov.spring.service;

import com.korshunov.spring.entity.Balance;

import java.util.List;

public interface BalanceService {
    List<Balance> listBalance();
    Balance create(Balance balance);
    void delete(Long id);
    Balance update(Balance balance);
    Balance findBalance(Long id);
}
