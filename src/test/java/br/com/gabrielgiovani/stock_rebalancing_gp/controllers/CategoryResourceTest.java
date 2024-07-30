package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.config.TestConfig;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.CategoryResponseDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.CategorySaveDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Category;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.User;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryController categoryResource;

    @Mock
    private CategoryService categoryServiceMock;

    @InjectMocks
    private CategoryController categoryResourceMock;

    private ObjectMapper objectMapper;

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
        ResponseEntity<List<CategoryResponseDTO>> response = categoryResource.findAll(gabrielAuthentication);

        List<CategoryResponseDTO> categoryResponseDTOS =  gabrielUser.getPortfolio().getCategories()
                .stream().map(CategoryResponseDTO::new).toList();

        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 1);
        assertEquals(categoryResponseDTOS, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findAll_NotFound() {
        when(categoryServiceMock.findAllByUsername(gabrielAuthentication.getName()))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<CategoryResponseDTO>> response = categoryResourceMock.findAll(gabrielAuthentication);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findById_Ok() {
        Integer categoryId = 1;

        ResponseEntity<CategoryResponseDTO> response = categoryResource.findById(gabrielAuthentication, categoryId);

        Optional<Category> categoryOptional = gabrielUser.getPortfolio().getCategories()
                .stream().filter(obj -> obj.getId().equals(1)).findFirst();

        Category category = categoryOptional.orElse(null);

        assertNotNull(category);
        assertNotNull(response.getBody());
        assertEquals(new CategoryResponseDTO(category), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

   @Test
    void findById_NotFound() {
        Integer categoryId = Integer.MAX_VALUE;

        ResponseEntity<CategoryResponseDTO> response = categoryResource.findById(gabrielAuthentication, categoryId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void insert_Created() {
        CategorySaveDTO categorySaveDTO = new CategorySaveDTO();
        categorySaveDTO.setName("Insert");
        categorySaveDTO.setDescription("Test description");
        categorySaveDTO.setPercentageUnderPortfolio(15.0);
        categorySaveDTO.setPortfolioId(1);

        ResponseEntity<CategoryResponseDTO> response = categoryResource.insert(gabrielAuthentication, categorySaveDTO);

        CategoryResponseDTO expectedCategoryResponseDTO = new CategoryResponseDTO(categorySaveDTO);
        expectedCategoryResponseDTO.setId(Objects.requireNonNull(response.getBody()).getId());

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(expectedCategoryResponseDTO, response.getBody());
        assertEquals(expectedCategoryResponseDTO.getPortfolioResponseDTO().getId(),
                response.getBody().getPortfolioResponseDTO().getId());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void update_Ok() {
        User user = TestConfig.getUserMap().get("teste");
        Authentication authentication = new TestingAuthenticationToken(user.getUsername(), null);

        CategorySaveDTO categorySaveDTO = new CategorySaveDTO();
        categorySaveDTO.setId(4);
        categorySaveDTO.setName("Update");
        categorySaveDTO.setDescription("Test description");
        categorySaveDTO.setPercentageUnderPortfolio(15.0);
        categorySaveDTO.setPortfolioId(2);

        ResponseEntity<CategoryResponseDTO> response = categoryResource.update(authentication, categorySaveDTO);

        assertNotNull(response.getBody());
        assertEquals(new CategoryResponseDTO(categorySaveDTO), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteById_Ok() {
        User user = TestConfig.getUserMap().get("test");
        Authentication authentication = new TestingAuthenticationToken(user.getUsername(), null);

        Integer categoryId = 5;

        ResponseEntity<Void> response = categoryResource.deleteById(authentication, categoryId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteById_NotFound() {
        Integer categoryId = Integer.MAX_VALUE;

        ResponseEntity<Void> response = categoryResource.deleteById(gabrielAuthentication, categoryId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}