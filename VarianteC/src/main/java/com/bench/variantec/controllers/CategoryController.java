package com.bench.variantec.controllers;

import com.bench.variantec.domain.Category;
import com.bench.variantec.repo.CategoryRepository;
import com.bench.variantec.repo.ItemRepository;
import com.bench.variantec.util.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepo;
    private final ItemRepository itemRepo;

    public CategoryController(CategoryRepository categoryRepo, ItemRepository itemRepo) {
        this.categoryRepo = categoryRepo;
        this.itemRepo = itemRepo;
    }

    @GetMapping
    public PageResponse<Category> list(Pageable pageable) {
        Page<Category> page = categoryRepo.findAll(pageable);
        return PageResponse.from(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> get(@PathVariable Long id) {
        return categoryRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category c) {
        return ResponseEntity.ok(categoryRepo.save(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @Valid @RequestBody Category c) {
        return categoryRepo.findById(id)
                .map(existing -> {
                    existing.setCode(c.getCode());
                    existing.setName(c.getName());
                    return ResponseEntity.ok(categoryRepo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!categoryRepo.existsById(id)) return ResponseEntity.notFound().build();
        categoryRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<?> itemsOfCategory(@PathVariable Long id, Pageable pageable) {
        if (!categoryRepo.existsById(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(itemRepo.findByCategoryId(id, pageable));
    }
}
