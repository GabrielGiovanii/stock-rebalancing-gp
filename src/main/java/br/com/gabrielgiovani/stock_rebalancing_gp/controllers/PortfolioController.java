package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.PortfolioDTO;
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
    public ResponseEntity<List<PortfolioDTO>> findAll(Authentication authentication) {
        String username = authentication.getName();

        List<Portfolio> portfolios = portfolioService.findAllByUsername(username);

        if(!portfolios.isEmpty()) {
            return ResponseEntity.ok().body(
                    portfolios.stream()
                    .map(PortfolioDTO::new)
                    .toList()
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PortfolioDTO> findById(Authentication authentication, @PathVariable Integer id) {
        String username = authentication.getName();

        Optional<Portfolio> portfolio = portfolioService.findByUsernameAndId(username, id);

        return portfolio.map(obj -> ResponseEntity.ok().body(new PortfolioDTO(obj)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
        public ResponseEntity<PortfolioDTO> insert(Authentication authentication, @Valid @RequestBody PortfolioDTO portfolioDTO) {
        String username = authentication.getName();

        Portfolio portfolio = portfolioService.createEntity(portfolioDTO);
        portfolio = portfolioService.insertOrUpdate(username, portfolio);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PortfolioDTO(portfolio));
    }

    @PutMapping
    public ResponseEntity<PortfolioDTO> update(Authentication authentication, @Valid @RequestBody PortfolioDTO portfolioDTO) {
        String username = authentication.getName();

        Portfolio portfolio = portfolioService.createEntity(portfolioDTO);
        portfolio = portfolioService.insertOrUpdate(username, portfolio);

        return ResponseEntity.status(HttpStatus.OK).body(new PortfolioDTO(portfolio));
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