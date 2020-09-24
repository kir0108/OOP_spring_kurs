package com.korshunov.spring.service;

import com.korshunov.spring.entity.Operation;

import java.util.List;

public interface OperationService {
    List<Operation> listOperation();
    Operation create(Operation balance);
    void delete(Long id);
    Operation update(Operation balance);
    Operation findOperation(Long id);
    List<Operation> findOperationWithArticle(Long id);
    List<Operation> findByFilter(String filter);
}
