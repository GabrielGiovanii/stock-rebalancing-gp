package br.com.gabrielgiovani.stock_rebalancing_gp.resources;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Category;
import br.com.gabrielgiovani.stock_rebalancing_gp.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categories = categoryService.findAll();

        if(!categories.isEmpty()) {
            return ResponseEntity.ok().body(categories);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Optional<Category> category = categoryService.findById(id);

        return category.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<?> insertOrUpdate(@Valid @RequestBody Category obj) {
        Category category = categoryService.insertOrUpdate(obj);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        if(categoryService.deleteById(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}