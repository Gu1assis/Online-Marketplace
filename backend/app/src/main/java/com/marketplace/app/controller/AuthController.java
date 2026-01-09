package com.marketplace.app.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {

        String senhaCriptografada = new BCryptPasswordEncoder().encode("123");
        System.out.println("HASH GERADO AGORA: " + senhaCriptografada);
        
        UsernamePasswordAuthenticationToken authenticationToken = 
            new UsernamePasswordAuthenticationToken(loginRequest.get("username"), loginRequest.get("password"));

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // No futuro, aqui geraremos o JWT
        return ResponseEntity.ok(Map.of(
            "message", "Login realizado com sucesso!",
            "user", authentication.getName()
        ));
    }
}