package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import br.com.gabrielgiovani.stock_rebalancing_gp.controllers.exceptions.EntityRelationshipNotFoundException;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.CompanyDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Company;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Sector;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.CompanyRepository;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.CRUDService;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.EntityCreationService;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.EntityRelationshipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CompanyService implements CRUDService<Company, Integer>,
        EntityCreationService<Company, CompanyDTO>, EntityRelationshipValidator {

    @Autowired
    private SectorService sectorService;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<Company> findAllByUsername(String username) {
        return companyRepository.findAllByUsername(username);
    }

    @Override
    public List<Company> findByUsernameAndFilters(String username, Map<String, Object> filters) {
        return null;
    }

    @Override
    public Optional<Company> findByUsernameAndId(String username, Integer integer) {
        return companyRepository.findByUsernameAndId(username, integer);
    }

    @Override
    public Company insertOrUpdate(String username, Company entity) {
        validateEntityRelationshipForInsertOrUpdateOrDelete(username, entity);

        return companyRepository.save(entity);
    }

    @Override
    public Boolean wasDeletedById(String username, Integer id) {
        if(companyRepository.existsById(id)) {
            validateEntityRelationshipForInsertOrUpdateOrDelete(username, id);
            companyRepository.deleteById(id);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public Company createEntity(CompanyDTO dto) {
        Company company = new Company();
        company.setId(dto.getId());;
        company.setCorporateName(dto.getCorporateName());
        company.setTradeName(dto.getTradeName());
        company.setCnpj(dto.getCnpj());
        company.setBookkeeperBank(dto.getBookkeeperBank());

        Sector sector = new Sector();
        sector.setId(dto.getSectorId());

        company.setSector(sector);
        sector.getCompanies().add(company);

        return company;
    }

    @Override
    public void validateEntityRelationshipForInsertOrUpdateOrDelete(String username, Object object) {
        boolean hasEntityRelationshipIssue = false;

        if(object instanceof Company company) {
            Optional<Sector> sector = sectorService.findByUsernameAndId(username, company.getSector().getId());

            if(sector.isPresent()) {
                company.setSector(sector.get());
            } else {
                hasEntityRelationshipIssue = true;
            }
        } else if(object instanceof Integer integer) {
            Optional<Company> company = companyRepository.findByUsernameAndId(username, integer);

            hasEntityRelationshipIssue = company.isEmpty();
        }

        if(hasEntityRelationshipIssue) {
            throw new EntityRelationshipNotFoundException();
        }
    }
}