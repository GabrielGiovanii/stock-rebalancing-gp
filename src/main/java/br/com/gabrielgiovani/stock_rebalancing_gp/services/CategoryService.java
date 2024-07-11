package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Category;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService implements CRUDService<Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findByFilters(Map<String, Object> filters) {
        return null;
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category insertOrUpdate(Category entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public boolean deleteById(Integer id) {
        if(categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void saveAll(List<Category> entities) {
        categoryRepository.saveAll(entities);
    }
}