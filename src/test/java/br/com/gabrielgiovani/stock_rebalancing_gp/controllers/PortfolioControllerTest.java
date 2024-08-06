package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.config.TestConfig;
import br.com.gabrielgiovani.stock_rebalancing_gp.config.UserTest;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.PortfolioDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.PortfolioService;
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
public class PortfolioControllerTest {

    @Autowired
    private PortfolioController portfolioResource;

    @Mock
    private PortfolioService portfolioServiceMock;

    @InjectMocks
    private PortfolioController portfolioResourceMock;

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
        ResponseEntity<List<PortfolioDTO>> response = portfolioResource.findAll(gabrielAuthentication);

        Set<PortfolioDTO> expectedSet = gabrielUserTest.getPortfolioSet().stream().map(PortfolioDTO::new).collect(Collectors.toSet());
        Set<PortfolioDTO> actualSet = new HashSet<>(Objects.requireNonNull(response.getBody()));

        assertNotNull(response.getBody());
        assertEquals(expectedSet, actualSet);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findAll_NotFound() {
        when(portfolioServiceMock.findAllByUsername(gabrielAuthentication.getName()))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<PortfolioDTO>> response = portfolioResourceMock.findAll(gabrielAuthentication);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findById_Ok() {
        Integer entityId = 1;

        ResponseEntity<PortfolioDTO> response = portfolioResource.findById(gabrielAuthentication, entityId);

        Optional<Portfolio> entityOptional = gabrielUserTest.getPortfolioSet()
                .stream().filter(obj -> obj.getId().equals(entityId)).findFirst();

        Portfolio entity = entityOptional.orElse(null);

        assertNotNull(entity);
        assertNotNull(response.getBody());
        assertEquals(new PortfolioDTO(entity), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findById_NotFound() {
        Integer entityId = Integer.MAX_VALUE;

        ResponseEntity<PortfolioDTO> response = portfolioResource.findById(gabrielAuthentication, entityId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void insert_Created() {
        UserTest userTest = TestConfig.getUserTestMap().get("test4");
        Authentication authentication = new TestingAuthenticationToken(userTest.getUsername(), null);

        PortfolioDTO entityDTO = new PortfolioDTO();
        entityDTO.setName("Insert");
        entityDTO.setDescription("Test description");
        entityDTO.setInvestmentPercentage(100.0);
        entityDTO.setUserId(4);

        ResponseEntity<PortfolioDTO> response = portfolioResource.insert(authentication, entityDTO);
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

        PortfolioDTO entityDTO = new PortfolioDTO();
        entityDTO.setId(2);
        entityDTO.setName("Update");
        entityDTO.setDescription("Test description");
        entityDTO.setInvestmentPercentage(95.5);
        entityDTO.setUserId(2);

        ResponseEntity<PortfolioDTO> response = portfolioResource.update(authentication, entityDTO);

        assertNotNull(response.getBody());
        assertEquals(entityDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteById_Ok() {
        UserTest userTest = TestConfig.getUserTestMap().get("test4");
        Authentication authentication = new TestingAuthenticationToken(userTest.getUsername(), null);

        Integer entityId = 4;

        ResponseEntity<Void> response = portfolioResource.deleteById(authentication, entityId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteById_NotFound() {
        Integer entityId = Integer.MAX_VALUE;

        ResponseEntity<Void> response = portfolioResource.deleteById(gabrielAuthentication, entityId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}