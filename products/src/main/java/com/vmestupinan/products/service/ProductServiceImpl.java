package com.vmestupinan.products.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vmestupinan.products.dto.ProductRequest;
import com.vmestupinan.products.dto.ProductResponse;
import com.vmestupinan.products.exception.ProductAlreadyExistsException;
import com.vmestupinan.products.model.Product;
import com.vmestupinan.products.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        log.info("Creating product: {}", request.getName());
        if (productRepository.existsByNameIgnoreCase(request.getName())) {
            log.warn("Product already exists with name: {}", request.getName());
            throw new ProductAlreadyExistsException("Product already exists with name: " + request.getName());
        }
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .status(request.getStatus())
                .build();

        Product saved = productRepository.save(product);
        log.info("Product created with ID: {}", saved.getId());
        return toResponse(saved);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        log.info("Retrieving all products");
        List<ProductResponse> products = productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        log.debug("Total products found: {}", products.size());
        return products;
    }

    @Override
    public ProductResponse getProductById(Long id) {
        log.info("Retrieving product with ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", id);
                    return new EntityNotFoundException("Product not found with id: " + id);
                });

        return toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        log.info("Updating product with ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found for update with ID: {}", id);
                    return new EntityNotFoundException("Product not found with id: " + id);
                });

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setStatus(request.getStatus());

        Product updated = productRepository.save(product);
        log.info("Product updated with ID: {}", updated.getId());
        return toResponse(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Deleting product with ID: {}", id);

        if (!productRepository.existsById(id)) {
            log.error("Product not found for deletion with ID: {}", id);
            throw new EntityNotFoundException("Product not found with id: " + id);
        }

        productRepository.deleteById(id);
        log.info("Product deleted with ID: {}", id);
    }

    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .status(product.getStatus())
                .build();
    }
}
