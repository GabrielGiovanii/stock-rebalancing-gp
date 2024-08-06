package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.*;
import br.com.gabrielgiovani.stock_rebalancing_gp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile(value = "test")
public class TestService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private CategorySectorRepository categorySectorRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public void saveAllUsers(List<User> entities) {
        entities.forEach(obj -> userService.encryptPassword(obj));

        userRepository.saveAll(entities);
    }

    public void saveAllPortfolios(List<Portfolio> entities) {
        portfolioRepository.saveAll(entities);
    }

    public void saveAllCategories(List<Category> entities) {
        categoryRepository.saveAll(entities);
    }

    public void saveAllSector(List<Sector> entities) {
        sectorRepository.saveAll(entities);
    }

    public void saveAllCategorySector(List<CategorySector> entities) {
        categorySectorRepository.saveAll(entities);
    }

    public void saveAllCompanies(List<Company> entities) {
        companyRepository.saveAll(entities);
    }
}