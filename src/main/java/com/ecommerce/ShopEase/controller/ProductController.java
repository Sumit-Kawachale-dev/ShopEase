package com.ecommerce.ShopEase.controller;

import com.sumitkawachale.ecommerce.ShopEase.config.AppConstants;
import com.sumitkawachale.ecommerce.ShopEase.payload.ProductDTO;
import com.sumitkawachale.ecommerce.ShopEase.payload.ProductResponse;
import com.sumitkawachale.ecommerce.ShopEase.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name="pageNumber",defaultValue= AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name="pageSize",defaultValue= AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name="sortBy",defaultValue= AppConstants.SORT_PRODUCT_BY,required = false) String sortBy,
            @RequestParam(name="sortOrder",defaultValue= AppConstants.SORT_DIR,required = false) String sortOrder
    ){
        ProductResponse productResponse=productService.getAllProducts(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getAllProductsByCategory(
            @RequestParam(name="pageNumber",defaultValue= AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name="pageSize",defaultValue= AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name="sortBy",defaultValue= AppConstants.SORT_PRODUCT_BY,required = false) String sortBy,
            @RequestParam(name="sortOrder",defaultValue= AppConstants.SORT_DIR,required = false) String sortOrder,
            @PathVariable Long categoryId
    ){
        ProductResponse productResponse=productService.searchByCategory(pageNumber,pageSize,sortBy,sortOrder,categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/search")
    public ResponseEntity<ProductResponse> getProductsByKeyword(
            @RequestParam(name="pageNumber",defaultValue= AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name="pageSize",defaultValue= AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name="sortBy",defaultValue= AppConstants.SORT_PRODUCT_BY,required = false) String sortBy,
            @RequestParam(name="sortOrder",defaultValue= AppConstants.SORT_DIR,required = false) String sortOrder,
            @RequestParam String keyword
    ){
        ProductResponse productResponse=productService.searchProductByKeyword(pageNumber,pageSize,sortBy,sortOrder,keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO,
                                                 @PathVariable Long categoryId){
        ProductDTO createdProductDTO=productService.createProduct(categoryId,productDTO);
        return new ResponseEntity<>(createdProductDTO, HttpStatus.CREATED);
    }

    @PutMapping("/admin/product/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody @Valid ProductDTO productDTO,
                                                 @PathVariable Long productId){
        ProductDTO updatedProductDTO=productService.updateProductById(productId,productDTO);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO deletedProductDTO=productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProductDTO,HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam(name="image")MultipartFile image) throws IOException {
        ProductDTO updatedProductDTO=productService.updateProductImage(productId,image);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }
}
