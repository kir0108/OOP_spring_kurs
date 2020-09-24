package com.korshunov.spring;

import com.korshunov.spring.entity.User;
import com.korshunov.spring.repository.OperationRepo;
import com.korshunov.spring.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TestDataInit implements CommandLineRunner {
    @Autowired
    OperationRepo operationRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder pwdEncoder;

    @Override
    public void run(String... args) throws Exception {
//        userRepo.save(new User("user", pwdEncoder.encode("pwd"), Collections.singletonList("ROLE_USER")));
//        userRepo.save(new User("ad", pwdEncoder.encode("ad"), Collections.singletonList("ROLE_ADMIN")));
    }
}
