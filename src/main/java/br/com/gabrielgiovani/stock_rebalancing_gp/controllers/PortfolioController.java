package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.PortfolioDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.PortfolioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<List<PortfolioDTO>> findAll() {
        List<PortfolioDTO> portfoliosDTOs = portfolioService.findAll();

        if(!portfoliosDTOs.isEmpty()) {
            return ResponseEntity.ok().body(portfoliosDTOs);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PortfolioDTO> findById(@PathVariable Integer id) {
            Optional<PortfolioDTO> portfolioDTO = portfolioService.findById(id);

            return portfolioDTO.map(value -> ResponseEntity.ok().body(value))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<PortfolioDTO> insert(@Valid @RequestBody PortfolioDTO portfolioDTO) {
        PortfolioDTO portfolioResponseDTO = portfolioService.insertOrUpdate(portfolioDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(portfolioResponseDTO);
    }

    @PutMapping
    public ResponseEntity<PortfolioDTO> update(@Valid @RequestBody PortfolioDTO portfolioDTO) {
        PortfolioDTO portfolioResponseDTO = portfolioService.insertOrUpdate(portfolioDTO);

        return ResponseEntity.status(HttpStatus.OK).body(portfolioResponseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        if(portfolioService.wasDeletedById(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}