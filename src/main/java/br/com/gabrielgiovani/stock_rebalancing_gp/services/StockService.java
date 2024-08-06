package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import br.com.gabrielgiovani.stock_rebalancing_gp.controllers.exceptions.EntityRelationshipNotFoundException;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.StockDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Company;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Stock;
import br.com.gabrielgiovani.stock_rebalancing_gp.enums.StockType;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.StockRepository;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.CRUDService;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.EntityCreationService;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.EntityRelationshipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class StockService implements CRUDService<Stock, Integer>,
        EntityCreationService<Stock, StockDTO>, EntityRelationshipValidator {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> findAllByUsername(String username) {
        return stockRepository.findAllByUsername(username);
    }

    @Override
    public List<Stock> findByUsernameAndFilters(String username, Map<String, Object> filters) {
        return null;
    }

    @Override
    public Optional<Stock> findByUsernameAndId(String username, Integer id) {
        return stockRepository.findByUsernameAndId(username, id);
    }

    @Override
    public Stock insertOrUpdate(String username, Stock entity) {
        validateEntityRelationshipForInsertOrUpdateOrDelete(username, entity);

        return stockRepository.save(entity);
    }

    @Override
    public Boolean wasDeletedById(String username, Integer id) {
        if(stockRepository.existsById(id)) {
            validateEntityRelationshipForInsertOrUpdateOrDelete(username, id);
            stockRepository.deleteById(id);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public Stock createEntity(StockDTO dto) {
        Stock stock = new Stock();
        stock.setId(dto.getId());
        stock.setTechnicalNote(dto.getTechnicalNote());
        stock.setQuotation(dto.getQuotation());
        stock.setTradingCode(dto.getTradingCode());
        stock.setType(Objects.requireNonNull(StockType.valueOf(dto.getStockTypeCode())));

        Company company = new Company();
        company.setId(dto.getCompanyId());

        stock.setCompany(company);
        company.getStocks().add(stock);

        return stock;
    }

    @Override
    public void validateEntityRelationshipForInsertOrUpdateOrDelete(String username, Object object) {
        boolean hasEntityRelationshipIssue = false;

        if(object instanceof Stock stock) {
            Optional<Company> company = companyService.findByUsernameAndId(username, stock.getCompany().getId());

            if(company.isPresent()) {
                stock.setCompany(company.get());
            } else {
                hasEntityRelationshipIssue = true;
            }
        } else if(object instanceof Integer integer) {
            Optional<Company> company = companyService.findByUsernameAndId(username, integer);

            hasEntityRelationshipIssue = company.isEmpty();
        }

        if(hasEntityRelationshipIssue) {
            throw new EntityRelationshipNotFoundException();
        }
    }
}