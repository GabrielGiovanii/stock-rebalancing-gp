package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.config.TestConfig;
import br.com.gabrielgiovani.stock_rebalancing_gp.config.UserTest;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.CategoryDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Category;
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

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryController categoryResource;

    @Mock
    private CategoryService categoryServiceMock;

    @InjectMocks
    private CategoryController categoryResourceMock;

    private ObjectMapper objectMapper;

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
        ResponseEntity<List<CategoryDTO>> response = categoryResource.findAll(gabrielAuthentication);

        Set<CategoryDTO> expectedSet = gabrielUserTest.getCategoryMap()
                .stream().map(CategoryDTO::new).collect(Collectors.toSet());
        Set<CategoryDTO> actualSet = new HashSet<>(Objects.requireNonNull(response.getBody()));

        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 1);
        assertEquals(expectedSet, actualSet);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findAll_NotFound() {
        when(categoryServiceMock.findAllByUsername(gabrielAuthentication.getName()))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<CategoryDTO>> response = categoryResourceMock.findAll(gabrielAuthentication);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findById_Ok() {
        Integer entityId = 1;

        ResponseEntity<CategoryDTO> response = categoryResource.findById(gabrielAuthentication, entityId);

        Optional<Category> entityOptional = gabrielUserTest.getCategoryMap()
                .stream().filter(obj -> obj.getId().equals(entityId)).findFirst();

        Category entity = entityOptional.orElse(null);

        assertNotNull(entity);
        assertNotNull(response.getBody());
        assertEquals(new CategoryDTO(entity), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

   @Test
    void findById_NotFound() {
        Integer categoryId = Integer.MAX_VALUE;

        ResponseEntity<CategoryDTO> response = categoryResource.findById(gabrielAuthentication, categoryId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void insert_Created() {
        CategoryDTO entityDTO = new CategoryDTO();
        entityDTO.setName("Insert");
        entityDTO.setDescription("Test description");
        entityDTO.setPercentageUnderPortfolio(15.0);
        entityDTO.setPortfolioId(1);

        ResponseEntity<CategoryDTO> response = categoryResource.insert(gabrielAuthentication, entityDTO);
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

        CategoryDTO entityDTO = new CategoryDTO();
        entityDTO.setId(4);
        entityDTO.setName("Update");
        entityDTO.setDescription("Test description");
        entityDTO.setPercentageUnderPortfolio(15.0);
        entityDTO.setPortfolioId(2);

        ResponseEntity<CategoryDTO> response = categoryResource.update(authentication, entityDTO);

        assertNotNull(response.getBody());
        assertEquals(entityDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteById_Ok() {
        UserTest userTest = TestConfig.getUserTestMap().get("test3");
        Authentication authentication = new TestingAuthenticationToken(userTest.getUsername(), null);

        Integer entityId = 6;

        ResponseEntity<Void> response = categoryResource.deleteById(authentication, entityId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteById_NotFound() {
        Integer entityId = Integer.MAX_VALUE;

        ResponseEntity<Void> response = categoryResource.deleteById(gabrielAuthentication, entityId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}