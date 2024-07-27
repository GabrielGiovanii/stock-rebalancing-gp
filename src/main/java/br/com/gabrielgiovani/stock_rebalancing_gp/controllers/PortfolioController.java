package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.PortfolioResponseDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.PortfolioSaveDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.PortfolioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<List<PortfolioResponseDTO>> findAll(Authentication authentication) {
        String username = authentication.getName();

        List<Portfolio> portfolios = portfolioService.findAllByUsername(username);

        if(!portfolios.isEmpty()) {
            return ResponseEntity.ok().body(
                    portfolios.stream()
                    .map(PortfolioResponseDTO::new)
                    .toList()
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PortfolioResponseDTO> findById(Authentication authentication, @PathVariable Integer id) {
        String username = authentication.getName();

        Optional<Portfolio> portfolio = portfolioService.findByUsernameAndId(username, id);

        return portfolio.map(obj -> ResponseEntity.ok().body(new PortfolioResponseDTO(obj)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<PortfolioResponseDTO> insert(Authentication authentication, @Valid @RequestBody PortfolioSaveDTO portfolioSaveDTO) {
        String username = authentication.getName();

        Portfolio portfolio = portfolioService.createEntity(portfolioSaveDTO);
        portfolio = portfolioService.insertOrUpdate(username, portfolio);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PortfolioResponseDTO(portfolio));
    }

    @PutMapping
    public ResponseEntity<PortfolioResponseDTO> update(Authentication authentication, @Valid @RequestBody PortfolioSaveDTO portfolioSaveDTO) {
        String username = authentication.getName();

        Portfolio portfolio = portfolioService.createEntity(portfolioSaveDTO);
        portfolio = portfolioService.insertOrUpdate(username, portfolio);

        return ResponseEntity.status(HttpStatus.OK).body(new PortfolioResponseDTO(portfolio));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(Authentication authentication, @PathVariable Integer id) {
        String username = authentication.getName();

        if(portfolioService.wasDeletedById(username, id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}