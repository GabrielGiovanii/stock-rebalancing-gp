package br.com.gabrielgiovani.stock_rebalancing_gp.resources;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.PortfolioService;
import br.com.gabrielgiovani.stock_rebalancing_gp.utils.DataConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/portfolios")
public class PortfolioResource {

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<List<Portfolio>> findAll() {
        List<Portfolio> wallets = portfolioService.findAll();
        return ResponseEntity.ok().body(wallets);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        try {
            Integer integerId = DataConversionUtils.convertToInteger(id);

            Optional<Portfolio> portfolio = portfolioService.findById(integerId);

            return portfolio.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<?> insertOrUpdate(@Valid @RequestBody Portfolio obj) {
}
