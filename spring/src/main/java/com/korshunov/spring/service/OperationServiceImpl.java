package com.korshunov.spring.service;


import com.korshunov.spring.entity.Operation;
import com.korshunov.spring.exception.OperationNotFoundException;
import com.korshunov.spring.repository.OperationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OperationServiceImpl implements OperationService{
    @Autowired
    private OperationRepo operationRepo;

    @Override
    public List<Operation> listOperation() {
        return (List<Operation>) operationRepo.findAll();
    }

    @Override
    public Operation create(Operation operation) {
        return operationRepo.save(operation);
    }

    @Override
    public void delete(Long id) {
        try {
            operationRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new OperationNotFoundException("Article not found");
        }
    }

    @Override
    public Operation update(Operation operation) {
        return operationRepo.save(operation);
    }

    @Override
    public Operation findOperation(Long id) {
        Optional<Operation> optionalOperation = operationRepo.findById(id);
        if (optionalOperation.isPresent()){
            return optionalOperation.get();
        } else {
            throw new OperationNotFoundException("Article not found");
        }
    }

    @Override
    public List<Operation> findOperationWithArticle(Long id) {
        List<Operation> operationList = (List<Operation>) operationRepo.findAll();
        List<Operation> list = new ArrayList<>();
        if (!operationList.isEmpty()){
            for (Operation operation: operationList){
                if (operation.getArticle().getId().equals(id)){
                    list.add(operation);
                }
            }

            return list;
        } else {
            return null;
        }
    }

    @Override
    public List<Operation> findByFilter(String filter) {
        List<Operation> operationList = (List<Operation>) operationRepo.findAll();
        List<Operation> operations = new ArrayList<>();
        if (!operationList.isEmpty()){
            for (Operation operation: operationList){
                if (operation.toString().contains(filter)){
                    operations.add(operation);
                }
            }

            return operations;
        } else {
            return null;
        }
    }
}
