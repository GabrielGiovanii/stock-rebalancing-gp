package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.CompanyDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Company;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> findAll(Authentication authentication) {
        String username = authentication.getName();

        List<Company> companies = companyService.findAllByUsername(username);

        if(!companies.isEmpty()) {
            return ResponseEntity.ok().body(
                    companies.stream()
                            .map(CompanyDTO::new)
                            .toList()
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyDTO> findById(Authentication authentication, @PathVariable Integer id) {
        String username = authentication.getName();

        Optional<Company> company = companyService.findByUsernameAndId(username, id);

        return company.map(obj -> ResponseEntity.ok().body(new CompanyDTO(obj)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> insert(Authentication authentication, @Valid @RequestBody CompanyDTO companyDTO) {
        String username = authentication.getName();

        Company company = companyService.createEntity(companyDTO);
        company = companyService.insertOrUpdate(username, company);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CompanyDTO(company));
    }

    @PutMapping
    public ResponseEntity<CompanyDTO> update(Authentication authentication, @Valid @RequestBody CompanyDTO companyDTO) {
        String username = authentication.getName();

        Company company = companyService.createEntity(companyDTO);
        company = companyService.insertOrUpdate(username, company);

        return ResponseEntity.status(HttpStatus.OK).body(new CompanyDTO(company));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(Authentication authentication, @PathVariable Integer id) {
        String username = authentication.getName();

        if(companyService.wasDeletedById(username, id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}