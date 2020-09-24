package com.korshunov.spring.service;

import com.korshunov.spring.entity.Balance;
import com.korshunov.spring.exception.BalanceNotFoundException;
import com.korshunov.spring.repository.BalanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BalanceServiceImpl implements BalanceService{
    @Autowired
    private BalanceRepo balanceRepo;

    @Override
    public List<Balance> listBalance() {
        return (List<Balance>) balanceRepo.findAll();
    }

    @Override
    public Balance create(Balance balance) {
        return balanceRepo.save(balance);
    }

    @Override
    public void delete(Long id) {
        try {
            balanceRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BalanceNotFoundException("Balance not found");
        }
    }

    @Override
    public Balance update(Balance balance) {
        return balanceRepo.save(balance);
    }

    @Override
    public Balance findBalance(Long id) {
        Optional<Balance> optionalBalance = balanceRepo.findById(id);
        if (optionalBalance.isPresent()){
            return optionalBalance.get();
        } else {
            throw new BalanceNotFoundException("Balance not found");
        }
    }
}
