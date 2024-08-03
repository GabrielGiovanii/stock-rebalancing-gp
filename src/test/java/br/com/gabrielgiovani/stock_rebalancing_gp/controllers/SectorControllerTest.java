package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.config.TestConfig;
import br.com.gabrielgiovani.stock_rebalancing_gp.config.UserTest;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.SectorDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Sector;
import br.com.gabrielgiovani.stock_rebalancing_gp.enums.SectorNature;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.SectorService;
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
public class SectorControllerTest {

    @Autowired
    private SectorController sectorController;

    @Mock
    private SectorService sectorServiceMock;

    @InjectMocks
    private SectorController sectorControllerMock;

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
        ResponseEntity<List<SectorDTO>> response = sectorController.findAll(gabrielAuthentication);

        Set<SectorDTO> expectedSet = gabrielUserTest.getSectorMap().stream().map(SectorDTO::new).collect(Collectors.toSet());
        Set<SectorDTO> actualSet = new HashSet<>(Objects.requireNonNull(response.getBody()));

        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 1);
        assertEquals(expectedSet, actualSet);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findAll_NotFound() {
        when(sectorServiceMock.findAllByUsername(gabrielAuthentication.getName()))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<SectorDTO>> response = sectorControllerMock.findAll(gabrielAuthentication);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findById_Ok() {
        Integer entityId = 1;

        ResponseEntity<SectorDTO> response = sectorController.findById(gabrielAuthentication, entityId);

        Optional<Sector> entityOptional = gabrielUserTest.getSectorMap()
                .stream().filter(obj -> obj.getId().equals(entityId)).findFirst();

        Sector entity = entityOptional.orElse(null);

        assertNotNull(entity);
        assertNotNull(response.getBody());
        assertEquals(new SectorDTO(entity), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findById_NotFound() {
        Integer entityId = Integer.MAX_VALUE;

        ResponseEntity<SectorDTO> response = sectorController.findById(gabrielAuthentication, entityId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void insert_Created() {
        SectorDTO entityDTO = new SectorDTO();
        entityDTO.setName("Insert");
        entityDTO.setDescription("Test description");
        entityDTO.setSectorNatureCode(SectorNature.CYCLICAL.getCode());
        entityDTO.getCategoryIds().add(1);
        entityDTO.getCategoryIds().add(2);

        ResponseEntity<SectorDTO> response = sectorController.insert(gabrielAuthentication, entityDTO);
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

        SectorDTO entityDTO = new SectorDTO();
        entityDTO.setId(4);
        entityDTO.setName("Update");
        entityDTO.setDescription("Test description");
        entityDTO.setSectorNatureCode(SectorNature.CYCLICAL.getCode());
        entityDTO.getCategoryIds().add(4);

        ResponseEntity<SectorDTO> response = sectorController.update(authentication, entityDTO);

        assertNotNull(response.getBody());
        assertEquals(entityDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteById_Ok() {
        UserTest userTest = TestConfig.getUserTestMap().get("test3");
        Authentication authentication = new TestingAuthenticationToken(userTest.getUsername(), null);

        Integer entityId = 5;

        ResponseEntity<Void> response = sectorController.deleteById(authentication, entityId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteById_NotFound() {
        Integer entityId = Integer.MAX_VALUE;

        ResponseEntity<Void> response = sectorController.deleteById(gabrielAuthentication, entityId);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}