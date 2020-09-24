package com.korshunov.spring.controller;

import com.korshunov.spring.entity.User;
import com.korshunov.spring.repository.UserRepo;
import com.korshunov.spring.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepo userRepo;

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody AuthRequest request){
        try {
            String name = request.getUserName();
            String password = request.getPassword();
            String token = jwtTokenProvider.createToken(
                    name, password,
                    userRepo.findUserByUserName(name)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found")).getRoles()
            );

            Map<Object, Object> model = new HashMap<>();
            model.put("userName", name);
            model.put("token", token);
            model.put("role", userRepo.findUserByUserName(name).get().getRoles());

            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody AuthRequest request) {
        try {
            String name = request.getUserName();
            String password = request.getPassword();
            if (userRepo.findUserByUserName(name).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            User user = new User(name, passwordEncoder.encode(password), Collections.singletonList("ROLE_USER"));
            userRepo.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Can't creat new user");
        }
    }
}
