package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.StockDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Stock;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.StockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<List<StockDTO>> findAll(Authentication authentication) {
        String username = authentication.getName();

        List<Stock> stocks = stockService.findAllByUsername(username);

        if(!stocks.isEmpty()) {
            return ResponseEntity.ok().body(
                    stocks.stream()
                            .map(StockDTO::new)
                            .toList()
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StockDTO> findById(Authentication authentication, @PathVariable Integer id) {
        String username = authentication.getName();

        Optional<Stock> stock = stockService.findByUsernameAndId(username, id);

        return stock.map(obj -> ResponseEntity.ok().body(new StockDTO(obj)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<StockDTO> insert(Authentication authentication, @Valid @RequestBody StockDTO stockDTO) {
        String username = authentication.getName();

        Stock stock = stockService.createEntity(stockDTO);
        stock = stockService.insertOrUpdate(username, stock);

        return ResponseEntity.status(HttpStatus.CREATED).body(new StockDTO(stock));
    }

    @PutMapping
    public ResponseEntity<StockDTO> update(Authentication authentication, @Valid @RequestBody StockDTO stockDTO) {
        String username = authentication.getName();

        Stock stock = stockService.createEntity(stockDTO);
        stock = stockService.insertOrUpdate(username, stock);

        return ResponseEntity.status(HttpStatus.OK).body(new StockDTO(stock));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(Authentication authentication, @PathVariable Integer id) {
        String username = authentication.getName();

        if(stockService.wasDeletedById(username, id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}