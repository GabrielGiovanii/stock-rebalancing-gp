package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.UserSaveDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.User;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.UserRepository;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.EntityCreationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements EntityCreationService<User, UserSaveDTO> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createEntity(UserSaveDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setFullName(dto.getFullName());

        encryptPassword(user);

        return user;
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByUsername(name);
    }

    public User insert(User entity) {
        return userRepository.save(entity);
    }

    public User update(String username, User entity) {
        validateEntityForUpdate(username, entity);

        return userRepository.save(entity);
    }

    public void encryptPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private void validateEntityForUpdate(String username, User entity) {
        boolean entityNotFound = false;

        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()) {
            entityNotFound = !Objects.equals(user.get().getId(), entity.getId());
        }

        if(entityNotFound) {
            throw new EntityNotFoundException();
        }
    }
}