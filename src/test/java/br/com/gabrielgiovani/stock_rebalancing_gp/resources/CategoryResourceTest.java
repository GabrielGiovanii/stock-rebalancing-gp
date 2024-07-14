package br.com.gabrielgiovani.stock_rebalancing_gp.resources;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.CategoryDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.CategoryService;
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
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryResource categoryResource;

    @Mock
    private CategoryService categoryServiceMock;

    @InjectMocks
    private CategoryResource categoryResourceMock;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();
    }

    @Test
    void findAll_Ok() {
        ResponseEntity<List<CategoryDTO>> response = categoryResource.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 1);
    }

    @Test
    void findAll_NotFound() {
        when(categoryServiceMock.findAll())
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<CategoryDTO>> response = categoryResourceMock.findAll();

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findById_Ok() {
        Integer categoryId = 1;

        ResponseEntity<CategoryDTO> response = categoryResource.findById(categoryId);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findById_NotFound() {
        Integer categoryId = Integer.MAX_VALUE;

        ResponseEntity<CategoryDTO> response = categoryResource.findById(categoryId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findById_BadRequest() throws Exception {
        String invalidCategoryId = "test";

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/categories/{id}", invalidCategoryId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void insert_Created() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Insert");
        categoryDTO.setDescription("Test description");
        categoryDTO.setPercentageUnderPortfolio(15.0);
        categoryDTO.setPortfolioId(1);

        ResponseEntity<CategoryDTO> response = categoryResource.insert(categoryDTO);

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(categoryDTO.getName(), response.getBody().getName());
        assertEquals(categoryDTO.getDescription(), response.getBody().getDescription());
        assertEquals(categoryDTO.getPercentageUnderPortfolio(), response.getBody().getPercentageUnderPortfolio());
        assertEquals(categoryDTO.getPortfolioId(), response.getBody().getPortfolioId());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void insert_BadRequest() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.percentageUnderPortfolio").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.portfolioId").exists());
    }

    @Test
    void update_Ok() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1);
        categoryDTO.setName("Update");
        categoryDTO.setDescription("Test description");
        categoryDTO.setPercentageUnderPortfolio(15.0);
        categoryDTO.setPortfolioId(1);

        ResponseEntity<CategoryDTO> response = categoryResource.update(categoryDTO);

        assertNotNull(response.getBody());
        assertEquals(categoryDTO.getId(), response.getBody().getId());
        assertEquals(categoryDTO.getName(), response.getBody().getName());
        assertEquals(categoryDTO.getDescription(), response.getBody().getDescription());
        assertEquals(categoryDTO.getPercentageUnderPortfolio(), response.getBody().getPercentageUnderPortfolio());
        assertEquals(categoryDTO.getPortfolioId(), response.getBody().getPortfolioId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updated_BadRequest() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();

        mockMvc.perform(MockMvcRequestBuilders.put("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.percentageUnderPortfolio").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.portfolioId").exists());
    }

    @Test
    void deleteById_Ok() {
        Integer categoryId = 2;

        ResponseEntity<Void> response = categoryResource.deleteById(categoryId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteById_NotFound() {
        Integer categoryId = Integer.MAX_VALUE;

        ResponseEntity<Void> response = categoryResource.deleteById(categoryId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteById_BadRequest() throws Exception {
        String invalidCategoryId = "test";

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/categories/{id}", invalidCategoryId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }
}