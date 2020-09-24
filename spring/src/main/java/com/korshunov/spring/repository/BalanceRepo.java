package com.korshunov.spring.repository;

import com.korshunov.spring.entity.Balance;
import org.springframework.data.repository.CrudRepository;

public interface BalanceRepo extends CrudRepository<Balance, Long> {
}
