package com.vmestupinan.products.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vmestupinan.products.dto.ProductRequest;
import com.vmestupinan.products.dto.ProductResponse;
import com.vmestupinan.products.exception.ProductAlreadyExistsException;
import com.vmestupinan.products.model.Category;
import com.vmestupinan.products.model.Product;
import com.vmestupinan.products.model.Status;
import com.vmestupinan.products.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void createProduct_shouldReturnProductResponse() {
        ProductRequest request = ProductRequest.builder()
                .name("Mouse")
                .description("Gaming mouse")
                .price(49.99)
                .category(Category.ELECTRONICS)
                .status(Status.AVAILABLE)
                .build();

        Product savedProduct = Product.builder()
                .id(1L)
                .name("Mouse")
                .description("Gaming mouse")
                .price(49.99)
                .category(Category.ELECTRONICS)
                .status(Status.AVAILABLE)
                .build();

        when(productRepository.existsByNameIgnoreCase("Mouse")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductResponse response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals("Mouse", response.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createProduct_shouldThrowWhenNameExists() {
        ProductRequest request = ProductRequest.builder()
                .name("Mouse")
                .build();

        when(productRepository.existsByNameIgnoreCase("Mouse")).thenReturn(true);

        ProductAlreadyExistsException ex = assertThrows(ProductAlreadyExistsException.class,
                () -> productService.createProduct(request));
        assertEquals("Product already exists with name: Mouse", ex.getMessage());
    }

    @Test
    void getProductById_shouldReturnProduct() {
        Product product = Product.builder()
                .id(1L)
                .name("Monitor")
                .description("4K Monitor")
                .price(199.99)
                .category(Category.ELECTRONICS)
                .status(Status.AVAILABLE)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(1L);

        assertEquals("Monitor", response.getName());
    }

    @Test
    void getProductById_shouldThrowWhenNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            productService.getProductById(1L);
        });
        assertEquals("Product not found with id: 1", ex.getMessage());
    }

    @Test
    void getAllProducts_shouldReturnList() {
        Product p1 = Product.builder().id(1L).name("Mouse").description("Gaming")
                .price(49.99).category(Category.ELECTRONICS).status(Status.AVAILABLE).build();
        Product p2 = Product.builder().id(2L).name("Keyboard").description("Mechanical")
                .price(79.99).category(Category.ELECTRONICS).status(Status.AVAILABLE).build();

        when(productRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProductResponse> products = productService.getAllProducts();

        assertEquals(2, products.size());
        assertEquals("Mouse", products.get(0).getName());
    }

    @Test
    void updateProduct_shouldUpdateAndReturnResponse() {
        Product existing = Product.builder()
                .id(1L).name("Old").description("Old").price(10.0)
                .category(Category.ELECTRONICS).status(Status.AVAILABLE).build();

        ProductRequest request = ProductRequest.builder()
                .name("New Mouse").description("Updated")
                .price(55.0).category(Category.ELECTRONICS).status(Status.AVAILABLE).build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenReturn(existing.toBuilder()
                .name("New Mouse").description("Updated").price(55.0).build());

        ProductResponse response = productService.updateProduct(1L, request);

        assertEquals("New Mouse", response.getName());
        assertEquals(55.0, response.getPrice());
    }

    @Test
    void deleteProduct_shouldDeleteSuccessfully() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProduct_shouldThrowWhenNotFound() {
        when(productRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> productService.deleteProduct(1L));

        assertEquals("Product not found with id: 1", ex.getMessage());
    }
}
