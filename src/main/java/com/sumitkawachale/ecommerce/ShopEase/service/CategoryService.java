package com.sumitkawachale.ecommerce.ShopEase.service;

import com.sumitkawachale.ecommerce.ShopEase.payload.CategoryDTO;
import com.sumitkawachale.ecommerce.ShopEase.payload.CategoryResponse;
import com.sumitkawachale.ecommerce.ShopEase.exceptions.APIException;
import com.sumitkawachale.ecommerce.ShopEase.exceptions.ResourceNotFoundException;
import com.sumitkawachale.ecommerce.ShopEase.model.Category;
import com.sumitkawachale.ecommerce.ShopEase.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        // Determine sort direction
        Sort sort = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // Set up pagination and sorting
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        // If no content, throw custom API exception
        if (categoryPage.isEmpty()) {
            throw new APIException("No categories found.");
        }

        // Convert Entity List to DTO List
        List<CategoryDTO> categoryDTOs = categoryPage.getContent().stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        // Build and return response
        CategoryResponse response = new CategoryResponse();
        response.setContent(categoryDTOs);
        response.setPageNumber(categoryPage.getNumber());
        response.setPageSize(categoryPage.getSize());
        response.setTotalElements(categoryPage.getTotalElements());
        response.setTotalPages(categoryPage.getTotalPages());
        response.setLastPage(categoryPage.isLast());

        return response;
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category existingCategory=categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if(existingCategory!=null)
            throw new APIException("Category with the "+categoryDTO.getCategoryName()+" already exists !");

        Category category=modelMapper.map(categoryDTO,Category.class);// Map DTO to entity
        Category savedCategory=categoryRepository.save(category);// Save the new category
        return modelMapper.map(savedCategory,CategoryDTO.class);// Map entity back to DTO and return
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        //It will return an Optional<Category> object if it Exists (because findById return type is Optional<>)
        //Or Else throw the ResponseStatusException (if not exists)
        Category existingCategory=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));

        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        Category updatedCategory= categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory,CategoryDTO.class);
    }

    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(category);
        return modelMapper.map(category,CategoryDTO.class);
    }

}
