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
    private PortfolioRepository walletRepository;

    @Override
    public List<Portfolio> findAll() {
        return walletRepository.findAll();
    }

    @Override
    public List<Portfolio> findByFilters(Map<String, Object> filters) {
        return null;
    }

    @Override
    public Optional<Portfolio> findById(Integer id) {
        return walletRepository.findById(id);
    }

    @Override
    public Portfolio saveOrUpdate(Portfolio entity) {
        Portfolio portfolio = portfolioRepository.save(entity);
        return portfolio;
    }

    @Override
    public void deleteById(Integer id) {
        walletRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<Portfolio> entities) {
        walletRepository.saveAll(entities);
    }
}
