package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.UserSaveDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.User;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.UserRepository;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.CRUDService;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.EntityCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements CRUDService<User>, EntityCreationService<User, UserSaveDTO> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findByFilters(Map<String, Object> filters) {
        return null;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public User insertOrUpdate(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public Boolean wasDeletedById(Integer id) {
        return null;
    }

    @Override
    public void saveAll(List<User> entities) {
    }

    @Override
    public User createEntity(UserSaveDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFullName(dto.getFullName());

        return user;
    }
}