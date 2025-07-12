package com.vmestupinan.products.service;

import java.util.List;

import com.vmestupinan.products.dto.ProductRequest;
import com.vmestupinan.products.dto.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    ProductResponse updateProduct(Long id, ProductRequest request);
    void deleteProduct(Long id);
}
