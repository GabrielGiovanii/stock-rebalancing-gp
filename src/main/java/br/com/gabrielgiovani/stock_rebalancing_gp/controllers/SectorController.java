package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.SectorDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Sector;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.SectorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "sectors")
public class SectorController {

    @Autowired
    private SectorService sectorService;

    @GetMapping
    public ResponseEntity<List<SectorDTO>> findAll(Authentication authentication) {
        String username = authentication.getName();

        List<Sector> sectors = sectorService.findAllByUsername(username);

        if(!sectors.isEmpty()) {
            return ResponseEntity.ok().body(
                    sectors.stream()
                            .map(SectorDTO::new)
                            .toList()
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SectorDTO> findById(Authentication authentication, @PathVariable Integer id) {
        String username = authentication.getName();

        Optional<Sector> sector = sectorService.findByUsernameAndId(username, id);

        return sector.map(obj -> ResponseEntity.ok().body(new SectorDTO(obj)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<SectorDTO> insert(Authentication authentication, @Valid @RequestBody SectorDTO sectorDTO) {
        String username = authentication.getName();

        Sector sector = sectorService.createEntity(sectorDTO);
        sector = sectorService.insertOrUpdate(username, sector);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SectorDTO(sector));
    }

    @PutMapping
    public ResponseEntity<SectorDTO> update(Authentication authentication, @Valid @RequestBody SectorDTO sectorDTO) {
        String username = authentication.getName();

        Sector sector = sectorService.createEntity(sectorDTO);
        sector = sectorService.insertOrUpdate(username, sector);

        return ResponseEntity.status(HttpStatus.OK).body(new SectorDTO(sector));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(Authentication authentication, @PathVariable Integer id) {
        String username = authentication.getName();

        if(sectorService.wasDeletedById(username, id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}