package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.UserResponseDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.UserSaveDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.User;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDTO> findByName(Authentication authentication) {
        String username = authentication.getName();

        Optional<User> user = userService.findByName(username);

        return user.map(obj -> ResponseEntity.ok().body(new UserResponseDTO(obj)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> insert(@Valid @RequestBody UserSaveDTO userSaveDTO) {
        User user = userService.createEntity(userSaveDTO);
        user = userService.insert(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDTO(user));
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> update(Authentication authentication, @Valid @RequestBody UserSaveDTO userSaveDTO) {
        String username = authentication.getName();

        User user = userService.createEntity(userSaveDTO);
        user = userService.update(username, user);

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDTO(user));
    }
}