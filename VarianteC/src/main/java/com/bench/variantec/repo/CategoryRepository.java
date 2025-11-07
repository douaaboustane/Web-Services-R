package com.bench.variantec.repo;

import com.bench.variantec.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCode(String code);
}
