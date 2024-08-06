package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.CategoryDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Category;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll(Authentication authentication) {
        String username = authentication.getName();

        List<Category> categories = categoryService.findAllByUsername(username);

        if(!categories.isEmpty()) {
            return ResponseEntity.ok().body(
                    categories.stream()
                    .map(CategoryDTO::new)
                    .toList()
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(Authentication authentication, @PathVariable Integer id) {
        String username = authentication.getName();

        Optional<Category> category = categoryService.findByUsernameAndId(username, id);

        return category.map(obj -> ResponseEntity.ok().body(new CategoryDTO(obj)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(Authentication authentication, @Valid @RequestBody CategoryDTO categoryDTO) {
        String username = authentication.getName();

        Category category = categoryService.createEntity(categoryDTO);
        category = categoryService.insertOrUpdate(username, category);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CategoryDTO(category));
    }

    @PutMapping
    public ResponseEntity<CategoryDTO> update(Authentication authentication, @Valid @RequestBody CategoryDTO categoryDTO) {
        String username = authentication.getName();

        Category category = categoryService.createEntity(categoryDTO);
        category = categoryService.insertOrUpdate(username, category);

        return ResponseEntity.status(HttpStatus.OK).body(new CategoryDTO(category));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(Authentication authentication, @PathVariable Integer id) {
        String username = authentication.getName();

        if(categoryService.wasDeletedById(username, id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}