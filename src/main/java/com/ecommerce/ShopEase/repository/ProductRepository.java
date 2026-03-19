package com.ecommerce.ShopEase.repository;

import com.sumitkawachale.ecommerce.ShopEase.model.Category;
import com.sumitkawachale.ecommerce.ShopEase.model.Product;
import com.sumitkawachale.ecommerce.ShopEase.payload.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategoryOrderByPriceAsc(Category category);

    Page<Product> findByCategory(Category category, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCase(String keyword, Pageable pageable);
}
