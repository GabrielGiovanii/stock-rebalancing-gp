package br.com.gabrielgiovani.stock_rebalancing_gp.resources;

import br.com.gabrielgiovani.stock_rebalancing_gp.dtos.CategoryDTO;
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
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> categoriesDTO = categoryService.findAll();

        if(!categoriesDTO.isEmpty()) {
            return ResponseEntity.ok().body(categoriesDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Optional<CategoryDTO> categoryDTO = categoryService.findById(id);

        return categoryDTO.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<?> insertOrUpdate(@Valid @RequestBody CategoryDTO obj) {
        CategoryDTO categoryDTO = categoryService.insertOrUpdate(obj);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
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