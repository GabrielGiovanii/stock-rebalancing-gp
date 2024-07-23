package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.CategoryDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Category;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.CategoryRepository;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService implements CRUDService<CategoryDTO> {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> findByFilters(Map<String, Object> filters) {
        return null;
    }

    @Override
    public Optional<CategoryDTO> findById(Integer id) {
        return categoryRepository.findById(id).map(CategoryDTO::new);
    }

    @Override
    public Optional<CategoryDTO> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public CategoryDTO insertOrUpdate(CategoryDTO entity) {
        Category category = new Category();
        category.setId(entity.getId());
        category.setName(entity.getName());
        category.setDescription(entity.getDescription());
        category.setPercentageUnderPortfolio(entity.getPercentageUnderPortfolio());

        Portfolio portfolio = new Portfolio();
        portfolio.setId(entity.getPortfolioId());

        category.setPortfolio(portfolio);
        portfolio.getCategories().add(category);

        return new CategoryDTO(categoryRepository.save(category));
    }

    @Override
    public Boolean wasDeletedById(Integer id) {
        if(categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void saveAll(List<CategoryDTO> entities) {
    }
}