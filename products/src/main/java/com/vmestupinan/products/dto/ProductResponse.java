package com.vmestupinan.products.dto;

import com.vmestupinan.products.model.Category;
import com.vmestupinan.products.model.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Category category;
    private Status status;
}
