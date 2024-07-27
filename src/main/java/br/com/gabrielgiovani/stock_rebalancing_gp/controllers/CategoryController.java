package br.com.gabrielgiovani.stock_rebalancing_gp.controllers;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.CategoryResponseDTO;
import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.CategorySaveDTO;
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
    public ResponseEntity<List<CategoryResponseDTO>> findAll(Authentication authentication) {
        String username = authentication.getName();

        List<Category> categories = categoryService.findAllByUsername(username);

        if(!categories.isEmpty()) {
            return ResponseEntity.ok().body(
                    categories.stream()
                    .map(CategoryResponseDTO::new)
                    .toList()
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(Authentication authentication, @PathVariable Integer id) {
        String username = authentication.getName();

        Optional<Category> category = categoryService.findByUsernameAndId(username, id);

        return category.map(obj -> ResponseEntity.ok().body(new CategoryResponseDTO(obj)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> insert(Authentication authentication, @Valid @RequestBody CategorySaveDTO categorySaveDTO) {
        String username = authentication.getName();

        Category category = categoryService.createEntity(categorySaveDTO);
        category = categoryService.insertOrUpdate(username, category);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CategoryResponseDTO(category));
    }

    @PutMapping
    public ResponseEntity<CategoryResponseDTO> update(Authentication authentication, @Valid @RequestBody CategorySaveDTO categorySaveDTO) {
        String username = authentication.getName();

        Category category = categoryService.createEntity(categorySaveDTO);
        category = categoryService.insertOrUpdate(username, category);

        return ResponseEntity.status(HttpStatus.OK).body(new CategoryResponseDTO(category));
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