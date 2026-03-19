package com.sumitkawachale.ecommerce.ShopEase.payload;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long productId;

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String productName;

    @NotBlank(message = "Product description is required")
    @Size(min = 6, max = 500, message = "Product description must be between 6 and 500 characters")
    private String description;

    private String image; // Validation depends on how you're storing/validating it

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be zero or more")
    private Integer quantity;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "49.0", inclusive = true, message = "Price must be at least 49.0")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid monetary amount")
    private Double price;

    @NotNull(message = "Discount is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Discount must be 0% or more")
    @DecimalMax(value = "85.0", inclusive = true, message = "Discount can't exceed 85%")
    @Digits(integer = 5, fraction = 2, message = "Discount must be a valid percentage")
    private Double discount;

    private Double specialPrice; // Usually computed, so no validation here
}