package br.com.gabrielgiovani.stock_rebalancing_gp.resources;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.UserResponseDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.UserSaveDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.User;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> insert(@Valid @RequestBody UserSaveDTO userSaveDTO) {
        User user = userService.createEntity(userSaveDTO);
        user = userService.insertOrUpdate(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDTO(user));
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> update(@Valid @RequestBody UserSaveDTO userSaveDTO) {
        User user = userService.createEntity(userSaveDTO);
        user = userService.insertOrUpdate(user);

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDTO(user));
    }
}