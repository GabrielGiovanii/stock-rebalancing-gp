package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.PortfolioDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.PortfolioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class PortfolioResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PortfolioController portfolioResource;

    @Mock
    private PortfolioService portfolioServiceMock;

    @InjectMocks
    private PortfolioController portfolioResourceMock;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();
    }

    @Test
    void findAll_Ok() {
        ResponseEntity<List<PortfolioDTO>> response = portfolioResource.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 1);
    }

    @Test
    void findAll_NotFound() {
        when(portfolioServiceMock.findAll())
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<PortfolioDTO>> response = portfolioResourceMock.findAll();

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findById_Ok() {
        Integer portfolioId = 1;

        ResponseEntity<PortfolioDTO> response = portfolioResource.findById(portfolioId);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findById_NotFound() {
        Integer portfolioId = Integer.MAX_VALUE;

        ResponseEntity<PortfolioDTO> response = portfolioResource.findById(portfolioId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findById_BadRequest() throws Exception {
        String invalidCategoryId = "test";

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/portfolios/{id}", invalidCategoryId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void insert_Created() {
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setName("Insert");
        portfolioDTO.setDescription("Test description");
        portfolioDTO.setInvestmentPercentage(100.0);

        ResponseEntity<PortfolioDTO> response = portfolioResource.insert(portfolioDTO);

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(portfolioDTO.getName(), response.getBody().getName());
        assertEquals(portfolioDTO.getDescription(), response.getBody().getDescription());
        assertEquals(portfolioDTO.getInvestmentPercentage(), response.getBody().getInvestmentPercentage());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void insert_BadRequest() throws Exception {
        PortfolioDTO portfolioDTO = new PortfolioDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(portfolioDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.investmentPercentage").exists());
    }

    @Test
    void update_Ok() {
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setId(1);
        portfolioDTO.setName("Update");
        portfolioDTO.setDescription("Test description");
        portfolioDTO.setInvestmentPercentage(95.5);

        ResponseEntity<PortfolioDTO> response = portfolioResource.update(portfolioDTO);

        assertNotNull(response.getBody());
        assertEquals(portfolioDTO.getId(), response.getBody().getId());
        assertEquals(portfolioDTO.getName(), response.getBody().getName());
        assertEquals(portfolioDTO.getDescription(), response.getBody().getDescription());
        assertEquals(portfolioDTO.getInvestmentPercentage(), response.getBody().getInvestmentPercentage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updated_BadRequest() throws Exception {
        PortfolioDTO portfolioDTO = new PortfolioDTO();

        mockMvc.perform(MockMvcRequestBuilders.put("/portfolios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(portfolioDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.investmentPercentage").exists());
    }

    @Test
    void deleteById_Ok() {
        Integer portfolioId = 3;

        ResponseEntity<Void> response = portfolioResource.deleteById(portfolioId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteById_NotFound() {
        Integer portfolioId = Integer.MAX_VALUE;

        ResponseEntity<Void> response = portfolioResource.deleteById(portfolioId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteById_BadRequest() throws Exception {
        String invalidCategoryId = "test";

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/portfolios/{id}", invalidCategoryId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }
}
