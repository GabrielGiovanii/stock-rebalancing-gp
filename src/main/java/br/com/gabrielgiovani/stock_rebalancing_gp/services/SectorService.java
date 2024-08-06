package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import br.com.gabrielgiovani.stock_rebalancing_gp.controllers.exceptions.EntityRelationshipNotFoundException;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.SectorDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Category;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.CategorySector;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Sector;
import br.com.gabrielgiovani.stock_rebalancing_gp.enums.SectorNature;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.SectorRepository;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.CRUDService;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.EntityCreationService;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts.EntityRelationshipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SectorService implements CRUDService<Sector, Integer>,
        EntityCreationService<Sector, SectorDTO>, EntityRelationshipValidator {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private CategorySectorService categorySectorService;

    @Override
    public List<Sector> findAllByUsername(String username) {
        return sectorRepository.findAllByUsername(username);
    }

    @Override
    public List<Sector> findByUsernameAndFilters(String username, Map<String, Object> filters) {
        return null;
    }

    @Override
    public Optional<Sector> findByUsernameAndId(String username, Integer id) {
        return sectorRepository.findByUsernameAndId(username, id);
    }

    @Override
    public Sector insertOrUpdate(String username, Sector entity) {
        validateEntityRelationshipForInsertOrUpdateOrDelete(username, entity);
        Sector sector = sectorRepository.save(entity);

        categorySectorService.saveAll(true, sector.getCategoriesSector().stream().toList());

        return sector;
    }

    @Override
    public Boolean wasDeletedById(String username, Integer id) {
        Optional<Sector> sector = sectorRepository.findByUsernameAndId(username, id);

        if(sector.isPresent()) {
            validateEntityRelationshipForInsertOrUpdateOrDelete(username, id);

            Boolean allCategoryServicesWereDeleted = false;
            Set<CategorySector> categorySectorSet = sector.get().getCategoriesSector();

            for(CategorySector categorySector : categorySectorSet) {
                allCategoryServicesWereDeleted = categorySectorService.wasDeletedById(username, categorySector.getId());

                if(!allCategoryServicesWereDeleted) {
                    break;
                }
            }

            if(allCategoryServicesWereDeleted) {
                sectorRepository.deleteById(id);
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public Sector createEntity(SectorDTO dto) {
        Sector sector = new Sector();
        sector.setId(dto.getId());
        sector.setName(dto.getName());
        sector.setDescription(dto.getDescription());

        SectorNature sectorNature = null;
        if(Objects.nonNull(dto.getSectorNatureCode())) {
            sectorNature = SectorNature.valueOf(dto.getSectorNatureCode());
            sector.setSectorNature(Objects.requireNonNull(sectorNature));
        }

        Set<Integer> categoryIds = dto.getCategoryIds();

        for(Integer id: categoryIds) {
            Category category = new Category();
            category.setId(id);

            CategorySector categorySector = new CategorySector();
            categorySector.setPercentUnderCategory(10.0);
            categorySector.getId().setCategory(category);
            categorySector.getId().setSector(sector);

            category.getCategorySectors().add(categorySector);
            sector.getCategoriesSector().add(categorySector);
        }

        return sector;
    }

    @Override
    public void validateEntityRelationshipForInsertOrUpdateOrDelete(String username, Object object) {
        boolean hasEntityRelationshipIssue = false;

        List<Category> categorySearchResults = categoryService.findAllByUsername(username);
        List<Integer> categoryIDsFromSearchResults = categorySearchResults
                .stream().map(Category::getId).toList();

        if(object instanceof Sector sector) {
            Set<CategorySector> categoriesSector = sector.getCategoriesSector();

            for(CategorySector cs : categoriesSector) {
                Integer categoryId = cs.getId().getCategory().getId();

                if(!categoryIDsFromSearchResults.contains(categoryId)) {
                    hasEntityRelationshipIssue = true;
                    break;
                }

                Optional<Category> categoryOptional = categorySearchResults
                        .stream().filter(obj -> obj.getId().equals(categoryId)).findAny();

                categoryOptional.ifPresent(obj -> cs.getId().setCategory(obj));
            }
        } else if(object instanceof Integer integer) {
            Optional<Sector> sectorOptional = sectorRepository.findByUsernameAndId(username, integer);

            hasEntityRelationshipIssue = sectorOptional.isEmpty();
        }

        if(hasEntityRelationshipIssue) {
            throw new EntityRelationshipNotFoundException();
        }
    }
}