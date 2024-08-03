package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.config.TestConfig;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.PortfolioDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.User;
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

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class PortfolioResourceTest {

    @Autowired
    private PortfolioController portfolioResource;

    @Mock
    private PortfolioService portfolioServiceMock;

    @InjectMocks
    private PortfolioController portfolioResourceMock;

    private static User gabrielUser;
    private static Authentication gabrielAuthentication;

    @BeforeEach
    void setUp() throws Exception {
        if(Objects.isNull(gabrielUser) || Objects.isNull(gabrielAuthentication)) {
            gabrielUser = TestConfig.getUserMap().get("gabriel");
            gabrielAuthentication = new TestingAuthenticationToken(gabrielUser.getUsername(), null);
        }
    }

    @Test
    void findAll_Ok() {
        ResponseEntity<List<PortfolioDTO>> response = portfolioResource.findAll(gabrielAuthentication);

        assertNotNull(response.getBody());
        assertEquals(new PortfolioDTO(gabrielUser.getPortfolio()), response.getBody().getFirst());
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
        Integer portfolioId = 1;

        ResponseEntity<PortfolioDTO> response = portfolioResource.findById(gabrielAuthentication, portfolioId);

        assertNotNull(response.getBody());
        assertEquals(new PortfolioDTO(gabrielUser.getPortfolio()), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findById_NotFound() {
        Integer portfolioId = Integer.MAX_VALUE;

        ResponseEntity<PortfolioDTO> response = portfolioResource.findById(gabrielAuthentication, portfolioId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void insert_Created() {
        User user = TestConfig.getUserMap().get("test123");
        Authentication authentication = new TestingAuthenticationToken(user.getUsername(), null);

        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setName("Insert");
        portfolioDTO.setDescription("Test description");
        portfolioDTO.setInvestmentPercentage(100.0);
        portfolioDTO.setUserId(4);

        ResponseEntity<PortfolioDTO> response = portfolioResource.insert(authentication, portfolioDTO);
        portfolioDTO.setId(Objects.requireNonNull(response.getBody()).getId());

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(portfolioDTO, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void update_Ok() {
        User user = TestConfig.getUserMap().get("teste");
        Authentication authentication = new TestingAuthenticationToken(user.getUsername(), null);

        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setId(2);
        portfolioDTO.setName("Update");
        portfolioDTO.setDescription("Test description");
        portfolioDTO.setInvestmentPercentage(95.5);
        portfolioDTO.setUserId(2);

        ResponseEntity<PortfolioDTO> response = portfolioResource.update(authentication, portfolioDTO);

        assertNotNull(response.getBody());
        assertEquals(portfolioDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteById_Ok() {
        User user = TestConfig.getUserMap().get("test");
        Authentication authentication = new TestingAuthenticationToken(user.getUsername(), null);

        Integer portfolioId = 3;

        ResponseEntity<Void> response = portfolioResource.deleteById(authentication, portfolioId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteById_NotFound() {
        Integer portfolioId = Integer.MAX_VALUE;

        ResponseEntity<Void> response = portfolioResource.deleteById(gabrielAuthentication, portfolioId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}