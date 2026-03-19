package com.sumitkawachale.ecommerce.ShopEase.service;

import com.sumitkawachale.ecommerce.ShopEase.exceptions.APIException;
import com.sumitkawachale.ecommerce.ShopEase.exceptions.ResourceNotFoundException;
import com.sumitkawachale.ecommerce.ShopEase.model.Category;
import com.sumitkawachale.ecommerce.ShopEase.model.Product;
import com.sumitkawachale.ecommerce.ShopEase.payload.ProductDTO;
import com.sumitkawachale.ecommerce.ShopEase.payload.ProductResponse;
import com.sumitkawachale.ecommerce.ShopEase.repository.CategoryRepository;
import com.sumitkawachale.ecommerce.ShopEase.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort=sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> productPages=productRepository.findAll(pageable);

        List<ProductDTO> products=productPages.getContent().stream()
                .map(product->modelMapper.map(product,ProductDTO.class))
                .toList();

        if(products.isEmpty())
            throw new APIException("No products found");

        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(products);
        productResponse.setPageNumber(productPages.getNumber());
        productResponse.setPageSize(productPages.getSize());
        productResponse.setTotalElements(productPages.getTotalElements());
        productResponse.setTotalPages(productPages.getTotalPages());
        productResponse.setLastPage(productPages.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Integer pageNumber, Integer pageSize, String sortBy,
                                            String sortOrder,Long categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));

        Sort sort=sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> productPages=productRepository.findByCategory(category,pageable);

        List<ProductDTO> products=productPages.getContent().stream()
                .map(product->modelMapper.map(product,ProductDTO.class))
                .toList();

        if(products.isEmpty())
            throw new APIException("Products Not found");

        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(products);
        productResponse.setPageNumber(productPages.getNumber());
        productResponse.setPageSize(productPages.getSize());
        productResponse.setTotalElements(productPages.getTotalElements());
        productResponse.setTotalPages(productPages.getTotalPages());
        productResponse.setLastPage(productPages.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder,String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new APIException("Keyword must not be null or empty");
        }

        Sort sort=sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> productPages=productRepository.findByProductNameContainingIgnoreCase(keyword,pageable);
        List<ProductDTO> products=productPages.getContent().stream()
                .map(product->modelMapper.map(product,ProductDTO.class))
                .toList();

        if(products.isEmpty())
            throw new APIException("Product Not Found by this keyword");

        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(products);
        productResponse.setPageNumber(productPages.getNumber());
        productResponse.setPageSize(productPages.getSize());
        productResponse.setTotalElements(productPages.getTotalElements());
        productResponse.setTotalPages(productPages.getTotalPages());
        productResponse.setLastPage(productPages.isLast());
        return productResponse;
    }

    @Override
    public ProductDTO updateProductById(Long productId, ProductDTO productDTO) {
        Product dbProduct=productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));

        dbProduct.setProductName(productDTO.getProductName());
        dbProduct.setDescription(productDTO.getDescription());
        dbProduct.setQuantity(productDTO.getQuantity());
        dbProduct.setPrice(productDTO.getPrice());
        dbProduct.setDiscount(productDTO.getDiscount());
        double specialPrice=productDTO.getPrice()-
                ((productDTO.getDiscount()*0.01) * productDTO.getPrice());
        dbProduct.setSpecialPrice(specialPrice);

        Product updatedProduct=productRepository.save(dbProduct);

        return modelMapper.map(updatedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO createProduct(Long categoryId, ProductDTO productDTO) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));

//        boolean exists = productRepository.existsByCategoryAndProductName(category, productDTO.getProductName());
//        if (exists) throw new APIException("Product already exists!!!");
        boolean isProductNotPresent=true;
        List<Product> products=category.getProducts();
        for(Product value:products){
            if(value.getProductName().equals(productDTO.getProductName())){
                isProductNotPresent=false;
                break;
            }
        }
        if(isProductNotPresent) {
            Product product = modelMapper.map(productDTO, Product.class);
            product.setImage(
                    productDTO.getImage() == null || productDTO.getImage().isEmpty()
                            ? "default.png"
                            : productDTO.getImage()
            );
            product.setCategory(category);
            double specialPrice = productDTO.getPrice() -
                    ((productDTO.getDiscount() * 0.01) * productDTO.getPrice());
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        }else{
            throw new APIException("Product already exists!!!");
        }
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));

        productRepository.delete(product);
        return modelMapper.map(product,ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product dbProduct=productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));

        String filename=fileService.uploadImage(path,image);
        dbProduct.setImage(filename);
        Product savedProduct=productRepository.save(dbProduct);
        return modelMapper.map(savedProduct,ProductDTO.class);
    }
}
