package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.config.TestConfig;
import br.com.gabrielgiovani.stock_rebalancing_gp.config.UserTest;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.CompanyDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Company;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @Autowired
    private CompanyController companyController;

    @Mock
    private CompanyService companyServiceMock;

    @InjectMocks
    private CompanyController companyControllerMock;

    private static UserTest gabrielUserTest;
    private static Authentication gabrielAuthentication;

    @BeforeEach
    void setUp() throws Exception {
        if(Objects.isNull(gabrielUserTest) || Objects.isNull(gabrielAuthentication)) {
            gabrielUserTest = TestConfig.getUserTestMap().get("gabriel");
            gabrielAuthentication = new TestingAuthenticationToken(gabrielUserTest.getUsername(), null);
        }
    }

    @Test
    void findAll_Ok() {
        ResponseEntity<List<CompanyDTO>> response = companyController.findAll(gabrielAuthentication);

        Set<CompanyDTO> expectedSet = gabrielUserTest.getCompanySet()
                .stream().map(CompanyDTO::new).collect(Collectors.toSet());
        Set<CompanyDTO> actualSet = new HashSet<>(Objects.requireNonNull(response.getBody()));

        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 1);
        assertEquals(expectedSet, actualSet);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findAll_NotFound() {
        when(companyServiceMock.findAllByUsername(gabrielAuthentication.getName()))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<CompanyDTO>> response = companyControllerMock.findAll(gabrielAuthentication);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findById_Ok() {
        Integer entityId = 1;

        ResponseEntity<CompanyDTO> response = companyController.findById(gabrielAuthentication, entityId);

        Optional<Company> entityOptional = gabrielUserTest.getCompanySet()
                .stream().filter(obj -> obj.getId().equals(entityId)).findFirst();

        Company entity = entityOptional.orElse(null);

        assertNotNull(entity);
        assertNotNull(response.getBody());
        assertEquals(new CompanyDTO(entity), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findById_NotFound() {
        Integer categoryId = Integer.MAX_VALUE;

        ResponseEntity<CompanyDTO> response = companyController.findById(gabrielAuthentication, categoryId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void insert_Created() {
        CompanyDTO entityDTO = new CompanyDTO();
        entityDTO.setCorporateName("Insert");
        entityDTO.setCnpj("12345678912345");
        entityDTO.setBookkeeperBank("Insert Bookkeeper Bank");
        entityDTO.setSectorId(1);

        ResponseEntity<CompanyDTO> response = companyController.insert(gabrielAuthentication, entityDTO);
        entityDTO.setId(Objects.requireNonNull(response.getBody()).getId());

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(entityDTO, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void update_Ok() {
        UserTest userTest = TestConfig.getUserTestMap().get("test2");
        Authentication authentication = new TestingAuthenticationToken(userTest.getUsername(), null);

        CompanyDTO entityDTO = new CompanyDTO();
        entityDTO.setId(6);
        entityDTO.setCorporateName("Update");
        entityDTO.setCnpj("12345678912345");
        entityDTO.setBookkeeperBank("Update Bookkeeper Bank");
        entityDTO.setSectorId(4);

        ResponseEntity<CompanyDTO> response = companyController.update(authentication, entityDTO);

        assertNotNull(response.getBody());
        assertEquals(entityDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteById_Ok() {
        UserTest userTest = TestConfig.getUserTestMap().get("test2");
        Authentication authentication = new TestingAuthenticationToken(userTest.getUsername(), null);

        Integer entityId = 6;

        ResponseEntity<Void> response = companyController.deleteById(authentication, entityId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteById_NotFound() {
        Integer entityId = Integer.MAX_VALUE;

        ResponseEntity<Void> response = companyController.deleteById(gabrielAuthentication, entityId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
