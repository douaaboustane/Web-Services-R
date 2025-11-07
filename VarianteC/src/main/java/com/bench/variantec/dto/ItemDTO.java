package com.bench.variantec.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ItemDTO(
        @NotBlank String sku,
        @NotBlank String name,
        @DecimalMin("0.00") BigDecimal price,
        @Min(0) Integer stock,
        @NotNull Long categoryId
) {}
