package br.com.gabrielgiovani.stock_rebalancing_gp.resources;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Portfolio;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/portfolios")
public class PortfolioResource {

    @Autowired
    private PortfolioService walletService;

    @GetMapping
    public ResponseEntity<List<Portfolio>> findAll() {
        List<Portfolio> wallets = walletService.findAll();
        return ResponseEntity.ok().body(wallets);
    }
}
