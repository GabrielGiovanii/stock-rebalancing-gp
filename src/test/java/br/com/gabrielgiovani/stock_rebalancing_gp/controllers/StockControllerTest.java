package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.config.TestConfig;
import br.com.gabrielgiovani.stock_rebalancing_gp.config.UserTest;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.StockDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Stock;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.StockService;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockController stockController;

    @Mock
    private StockService stockServiceMock;

    @InjectMocks
    private StockController stockControllerMock;
    
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
        ResponseEntity<List<StockDTO>> response = stockController.findAll(gabrielAuthentication);

        Set<StockDTO> expectedSet = gabrielUserTest.getStockSet()
                .stream().map(StockDTO::new).collect(Collectors.toSet());
        Set<StockDTO> actualSet = new HashSet<>(Objects.requireNonNull(response.getBody()));

        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 1);
        assertEquals(expectedSet, actualSet);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findAll_NotFound() {
        when(stockServiceMock.findAllByUsername(gabrielAuthentication.getName()))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<StockDTO>> response = stockControllerMock.findAll(gabrielAuthentication);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findById_Ok() {
        Integer entityId = 1;

        ResponseEntity<StockDTO> response = stockController.findById(gabrielAuthentication, entityId);

        Optional<Stock> entityOptional = gabrielUserTest.getStockSet()
                .stream().filter(obj -> obj.getId().equals(entityId)).findFirst();

        Stock entity = entityOptional.orElse(null);

        assertNotNull(entity);
        assertNotNull(response.getBody());
        assertEquals(new StockDTO(entity), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findById_NotFound() {
        Integer categoryId = Integer.MAX_VALUE;

        ResponseEntity<StockDTO> response = stockController.findById(gabrielAuthentication, categoryId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void insert_Created() {
        StockDTO entityDTO = new StockDTO();
        entityDTO.setTechnicalNote(7.5);
        entityDTO.setQuotation(17.5);
        entityDTO.setTradingCode("ITUB4");
        entityDTO.setStockTypeCode(2);
        entityDTO.setCompanyId(2);

        ResponseEntity<StockDTO> response = stockController.insert(gabrielAuthentication, entityDTO);
        entityDTO.setId(Objects.requireNonNull(response.getBody()).getId());

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(entityDTO, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void update_Ok() {
        UserTest userTest = TestConfig.getUserTestMap().get("test3");
        Authentication authentication = new TestingAuthenticationToken(userTest.getUsername(), null);

        StockDTO entityDTO = new StockDTO();
        entityDTO.setId(8);
        entityDTO.setTechnicalNote(5.8);
        entityDTO.setQuotation(14.5);
        entityDTO.setTradingCode("CURY3");
        entityDTO.setStockTypeCode(1);
        entityDTO.setCompanyId(7);

        ResponseEntity<StockDTO> response = stockController.update(authentication, entityDTO);

        assertNotNull(response.getBody());
        assertEquals(entityDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteById_Ok() {
        UserTest userTest = TestConfig.getUserTestMap().get("test3");
        Authentication authentication = new TestingAuthenticationToken(userTest.getUsername(), null);

        Integer entityId = 7;

        ResponseEntity<Void> response = stockController.deleteById(authentication, entityId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteById_NotFound() {
        Integer entityId = Integer.MAX_VALUE;

        ResponseEntity<Void> response = stockController.deleteById(gabrielAuthentication, entityId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}