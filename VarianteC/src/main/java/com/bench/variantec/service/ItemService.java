package com.bench.variantec.service;

import com.bench.variantec.domain.Item;
import com.bench.variantec.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepository repo;

    @Value("${bench.joinfetch.enabled:true}")
    private boolean joinFetchEnabled;

    public ItemService(ItemRepository repo) { this.repo = repo; }

    public Page<Item> list(Pageable p) { return repo.findAll(p); }

    public Page<Item> byCategory(Long categoryId, Pageable p) {
        return joinFetchEnabled ? repo.findByCategoryIdJoinFetch(categoryId, p)
                : repo.findByCategoryId(categoryId, p);
    }
}
