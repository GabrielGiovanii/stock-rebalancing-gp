package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.PortfolioDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.PortfolioRepository;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PortfolioService implements CRUDService<PortfolioDTO> {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public List<PortfolioDTO> findAll() {
        return portfolioRepository.findAll().stream().map(PortfolioDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<PortfolioDTO> findByFilters(Map<String, Object> filters) {
        return null;
    }

    @Override
    public Optional<PortfolioDTO> findById(Integer id) {
        return portfolioRepository.findById(id).map(PortfolioDTO::new);
    }

    @Override
    public Optional<PortfolioDTO> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public PortfolioDTO insertOrUpdate(PortfolioDTO entity) {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(entity.getId());
        portfolio.setName(entity.getName());
        portfolio.setDescription(entity.getDescription());
        portfolio.setInvestmentPercentage(entity.getInvestmentPercentage());

        return new PortfolioDTO(portfolioRepository.save(portfolio));
    }

    @Override
    public Boolean wasDeletedById(Integer id) {
        if(portfolioRepository.existsById(id)) {
            portfolioRepository.deleteById(id);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void saveAll(List<PortfolioDTO> entities) {
    }
}