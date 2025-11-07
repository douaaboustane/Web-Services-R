package com.bench.variantec.controllers;

import com.bench.variantec.domain.Category;
import com.bench.variantec.domain.Item;
import com.bench.variantec.repo.CategoryRepository;
import com.bench.variantec.repo.ItemRepository;
import com.bench.variantec.service.ItemService;
import com.bench.variantec.util.PageResponse;
import com.bench.variantec.dto.ItemDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepo;
    private final CategoryRepository categoryRepo;

    public ItemController(ItemService itemService, ItemRepository itemRepo, CategoryRepository categoryRepo) {
        this.itemService = itemService;
        this.itemRepo = itemRepo;
        this.categoryRepo = categoryRepo;
    }

    @GetMapping
    public PageResponse<Item> list(Pageable pageable) {
        Page<Item> page = itemService.list(pageable);
        return PageResponse.from(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> get(@PathVariable Long id) {
        return itemRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "categoryId")
    public PageResponse<Item> byCategory(@RequestParam Long categoryId, Pageable pageable) {
        Page<Item> page = itemService.byCategory(categoryId, pageable);
        return PageResponse.from(page);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ItemDTO dto) {
        Category cat = categoryRepo.findById(dto.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("categoryId not found"));
        Item e = Item.builder()
                .sku(dto.sku()).name(dto.name())
                .price(dto.price()).stock(dto.stock())
                .category(cat).build();
        return ResponseEntity.ok(itemRepo.save(e));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ItemDTO dto) {
        return itemRepo.findById(id).map(e -> {
            Category cat = categoryRepo.findById(dto.categoryId())
                    .orElseThrow(() -> new IllegalArgumentException("categoryId not found"));
            e.setSku(dto.sku());
            e.setName(dto.name());
            e.setPrice(dto.price());
            e.setStock(dto.stock());
            e.setCategory(cat);
            return ResponseEntity.ok(itemRepo.save(e));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!itemRepo.existsById(id)) return ResponseEntity.notFound().build();
        itemRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
