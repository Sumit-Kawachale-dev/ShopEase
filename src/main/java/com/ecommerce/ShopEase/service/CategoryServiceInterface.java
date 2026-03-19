package com.ecommerce.ShopEase.service;

import com.sumitkawachale.ecommerce.ShopEase.model.Category;

import java.util.List;

public interface CategoryServiceInterface {
    List<Category> getCategory();
    void createCategory(Category category);
    String deleteCategory(Long categoryId);

    String updateCategory(Category category, Long categoryId);
}
