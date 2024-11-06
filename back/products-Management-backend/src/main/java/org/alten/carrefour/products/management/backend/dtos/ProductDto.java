package org.alten.carrefour.products.management.backend.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    private String code;

    private String name;

    private String description;

    private String image;

    private String category;

    private Double price;

    private Integer quantity;

    private String internalReference;

    private Integer shellId;

    private String rating;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private InventoryStatus inventoryStatus;

}
