package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import br.com.gabrielgiovani.stock_rebalancing_gp.controllers.exceptions.EntityRelationshipNotFoundException;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.CategorySector;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.pk.CategorySectorPK;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.CategorySectorRepository;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.CRUDService;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.EntityRelationshipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategorySectorService implements CRUDService<CategorySector, CategorySectorPK>,
        EntityRelationshipValidator {

    @Autowired
    private CategorySectorRepository categorySectorRepository;

    @Override
    public List<CategorySector> findAllByUsername(String username) {
        return categorySectorRepository.findAllByUsername(username);
    }

    @Override
    public List<CategorySector> findByUsernameAndFilters(String username, Map<String, Object> filters) {
        return null;
    }

    @Override
    public Optional<CategorySector> findByUsernameAndId(String username, CategorySectorPK id) {
        return categorySectorRepository.findAllByUsernameAndId(username,
                id.getCategory().getId(), id.getSector().getId());
    }

    @Override
    public CategorySector insertOrUpdate(String username, CategorySector entity) {
        validateEntityRelationshipForInsertOrUpdateOrDelete(username, entity);

        return categorySectorRepository.save(entity);
    }

    @Override
    public Boolean wasDeletedById(String username, CategorySectorPK id) {
        if(categorySectorRepository.existsById(id)) {
            validateEntityRelationshipForInsertOrUpdateOrDelete(username, id);
            categorySectorRepository.deleteById(id);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void validateEntityRelationshipForInsertOrUpdateOrDelete(String username, Object object) {
        boolean hasEntityRelationshipIssue = false;

        if(object instanceof CategorySector categorySector) {
            Optional<CategorySector> categorySectorOptional = categorySectorRepository
                    .findAllByUsernameAndId(username,
                            categorySector.getId().getCategory().getId(), categorySector.getId().getSector().getId());

            hasEntityRelationshipIssue = categorySectorOptional.isEmpty();
        } else if(object instanceof CategorySectorPK categorySectorPK) {
            Optional<CategorySector> categorySectorOptional = categorySectorRepository
                    .findAllByUsernameAndId(username,
                            categorySectorPK.getCategory().getId(), categorySectorPK.getSector().getId());

            hasEntityRelationshipIssue = categorySectorOptional.isEmpty();
        }

        if(hasEntityRelationshipIssue) {
            throw new EntityRelationshipNotFoundException();
        }
    }

    public void saveAll(boolean isRelationshipValidated, List<CategorySector> entities) {
        if(isRelationshipValidated) {
            categorySectorRepository.saveAll(entities);
        }
    }
}