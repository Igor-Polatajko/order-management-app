package com.pnu.ordermanagementapp.service;

import com.pnu.ordermanagementapp.dto.product.ProductFormSubmitDto;
import com.pnu.ordermanagementapp.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    List<Product> findAll(Long userId);

    List<Product> findAllActive(Long userId);

    Page<Product> findAllByActivity(Integer pageNumber, boolean isActive, Long userId);

    Page<Product> findAllByNameAndActivity(Integer pageNumber, String name, boolean isActive, Long userId);

    void activate(Long id, Long userId);

    Product findById(Long id, Long userId);

    void create(ProductFormSubmitDto productFormDto, Long userId);

    void update(ProductFormSubmitDto productFormDto, Long userId);

    void deactivate(Long id, Long userId);

}
