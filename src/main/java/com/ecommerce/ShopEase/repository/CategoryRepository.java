package com.ecommerce.ShopEase.repository;

import com.sumitkawachale.ecommerce.ShopEase.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByCategoryName(String categoryName);
}
