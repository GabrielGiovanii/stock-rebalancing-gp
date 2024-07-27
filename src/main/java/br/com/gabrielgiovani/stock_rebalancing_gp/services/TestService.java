package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Category;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.User;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.CategoryRepository;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.PortfolioRepository;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile(value = "test")
public class TestService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void saveAllUsers(List<User> entities) {
        entities.forEach(obj -> userService.encryptPassword(obj));

        userRepository.saveAll(entities);
    }

    public void saveAllPortfolios(List<Portfolio> entities) {
        portfolioRepository.saveAll(entities);
    }

    public void saveAllCategories(List<Category> entities) {
        categoryRepository.saveAll(entities);
    }
}