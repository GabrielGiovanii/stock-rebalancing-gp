package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.services.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/auth")
    public ResponseEntity<Map<String, String>> authenticate(Authentication authentication) {
        Map<String, String> response = new HashMap<>();

        String generatedToken = authService.authenticate(authentication);
        response.put("token", generatedToken);

        if(Objects.isNull(generatedToken)) {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }
}