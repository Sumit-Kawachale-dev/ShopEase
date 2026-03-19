package com.ecommerce.ShopEase.service;

import com.sumitkawachale.ecommerce.ShopEase.payload.ProductDTO;
import com.sumitkawachale.ecommerce.ShopEase.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO createProduct(Long categoryId, ProductDTO productDTO);

    ProductResponse searchByCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder,Long categoryId);

    ProductResponse searchProductByKeyword(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder,String keyword);

    ProductDTO updateProductById(Long productId, ProductDTO productDTO);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
}
