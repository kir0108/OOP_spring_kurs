package com.korshunov.spring.repository;

import com.korshunov.spring.entity.Operation;
import org.springframework.data.repository.CrudRepository;

public interface OperationRepo extends CrudRepository<Operation, Long> {
}
