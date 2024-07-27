package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import br.com.gabrielgiovani.stock_rebalancing_gp.controllers.exceptions.EntityRelationshipNotFoundException;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.PortfolioSaveDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.User;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.PortfolioRepository;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.CRUDService;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.EntityCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class PortfolioService implements CRUDService<Portfolio>,
        EntityCreationService<Portfolio, PortfolioSaveDTO> {

    @Autowired
    private UserService userService;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public List<Portfolio> findAllByUsername(String username) {
        return portfolioRepository.findAllByUsername(username);
    }

    @Override
    public List<Portfolio> findByUsernameAndFilters(String username, Map<String, Object> filters) {
        return null;
    }

    @Override
    public Optional<Portfolio> findByUsernameAndId(String username, Integer id) {
        return portfolioRepository.findByUsernameAndId(username, id);
    }

    @Override
    public Portfolio insertOrUpdate(String username, Portfolio entity) {
        validateEntityRelationshipForInsertOrUpdateOrDelete(username, entity);

        return portfolioRepository.save(entity);
    }

    @Override
    public Boolean wasDeletedById(String username, Integer id) {
        if(portfolioRepository.existsById(id)) {
            validateEntityRelationshipForInsertOrUpdateOrDelete(username, id);
            portfolioRepository.deleteById(id);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void saveAll(String username, List<Portfolio> entities) {
    }

    @Override
    public Portfolio createEntity(PortfolioSaveDTO dto) {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(dto.getId());
        portfolio.setName(dto.getName());
        portfolio.setDescription(dto.getDescription());
        portfolio.setInvestmentPercentage(dto.getInvestmentPercentage());

        User user = new User();
        user.setId(dto.getUserId());

        portfolio.setUser(user);
        user.setPortfolio(portfolio);

        return portfolio;
    }

    private void validateEntityRelationshipForInsertOrUpdateOrDelete(String username, Object object) {
        boolean hasEntityRelationshipIssue = false;

        Optional<User> user = userService.findByName(username);

        if(user.isPresent()) {
            if(object instanceof Portfolio portfolio) {
                hasEntityRelationshipIssue = !Objects.equals(user.get().getId(), portfolio.getUser().getId());
            } else if(object instanceof Integer integer) {
                Optional<Portfolio> portfolio = portfolioRepository.findByUsernameAndId(username, integer);

                hasEntityRelationshipIssue = portfolio.isEmpty();
            }
        }

        if(hasEntityRelationshipIssue) {
            throw new EntityRelationshipNotFoundException();
        }
    }
}