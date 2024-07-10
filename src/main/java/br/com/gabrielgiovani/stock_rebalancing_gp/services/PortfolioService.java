package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PortfolioService implements CRUDService<Portfolio> {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public List<Portfolio> findAll() {
        return portfolioRepository.findAll();
    }

    @Override
    public List<Portfolio> findByFilters(Map<String, Object> filters) {
        return null;
    }

    @Override
    public Optional<Portfolio> findById(Integer id) {
        return portfolioRepository.findById(id);
    }

    @Override
    public Portfolio insertOrUpdate(Portfolio entity) {
        Portfolio portfolio = portfolioRepository.save(entity);
        return portfolio;
    }

    @Override
    public void deleteById(Integer id) {
        portfolioRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<Portfolio> entities) {
        portfolioRepository.saveAll(entities);
    }
}
