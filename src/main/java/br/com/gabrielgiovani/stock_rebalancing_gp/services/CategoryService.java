package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import br.com.gabrielgiovani.stock_rebalancing_gp.controllers.exceptions.EntityRelationshipNotFoundException;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.CategorySaveDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Category;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.CategoryRepository;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService implements CRUDService<Category, Integer>,
        EntityCreationService<Category, CategorySaveDTO>, EntityRelationshipValidator {

    @Autowired
    private UserService userService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllByUsername(String username) {
        return categoryRepository.findAllByUsername(username);
    }

    @Override
    public List<Category> findByUsernameAndFilters(String username, Map<String, Object> filters) {
        return null;
    }

    @Override
    public Optional<Category> findByUsernameAndId(String username, Integer id) {
        return categoryRepository.findByUsernameAndId(username, id);
    }

    @Override
    public Category insertOrUpdate(String username, Category entity) {
        validateEntityRelationshipForInsertOrUpdateOrDelete(username, entity);

        return categoryRepository.save(entity);
    }

    @Override
    public Boolean wasDeletedById(String username, Integer id) {
        if(categoryRepository.existsById(id)) {
            validateEntityRelationshipForInsertOrUpdateOrDelete(username, id);
            categoryRepository.deleteById(id);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public Category createEntity(CategorySaveDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setPercentageUnderPortfolio(dto.getPercentageUnderPortfolio());

        Portfolio portfolio = new Portfolio();
        portfolio.setId(dto.getPortfolioId());

        category.setPortfolio(portfolio);
        portfolio.getCategories().add(category);

        return category;
    }

    @Override
    public void validateEntityRelationshipForInsertOrUpdateOrDelete(String username, Object object) {
        boolean hasEntityRelationshipIssue = false;

        if(object instanceof Category category) {
            Optional<Portfolio> portfolio = portfolioService.findByUsernameAndId(username, category.getPortfolio().getId());

            if(portfolio.isPresent()) {
                category.setPortfolio(portfolio.get());
            } else {
                hasEntityRelationshipIssue = true;
            }
        } else if(object instanceof Integer integer) {
            Optional<Category> category = categoryRepository.findByUsernameAndId(username, integer);

            hasEntityRelationshipIssue = category.isEmpty();
        }

        if(hasEntityRelationshipIssue) {
            throw new EntityRelationshipNotFoundException();
        }
    }
}