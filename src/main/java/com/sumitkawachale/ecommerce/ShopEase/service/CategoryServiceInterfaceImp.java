package com.sumitkawachale.ecommerce.ShopEase.service;

import com.sumitkawachale.ecommerce.ShopEase.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceInterfaceImp implements CategoryServiceInterface {
    List<Category> categories=new ArrayList<>();
    private Long idCounter = 1L;

    @Override
    public List<Category> getCategory() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(idCounter++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category=categories.stream()
                .filter(c->c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resourse not found"));

        categories.remove(category);
        return "Category with ID " + categoryId + " Deleted successfully!";
    }

    @Override
    public String updateCategory(Category category, Long categoryId) {
//        Optional<Category> optionalCategory=categories.stream()
//                .filter(c->c.getCategoryId().equals(categoryId))
//                .findFirst();
//
//        if(optionalCategory.isPresent()){
//            Category existigCategory= optionalCategory.get();
//            existigCategory.setCategoryName(category.getCategoryName());
//            return "Category with ID " + categoryId + " updated successfully!";
//        }else{
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found");
//        }

        Category updatCeategory=categories.stream()
                .filter(c->c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resourse not found"));

        updatCeategory.setCategoryName(category.getCategoryName());
        return "Category with ID " + categoryId + " updated successfully!";
    }
}
