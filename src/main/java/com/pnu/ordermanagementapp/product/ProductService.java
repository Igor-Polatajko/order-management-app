package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    List<Product> findAllActive(Long userId);

    Page<Product> findAllByActivity(Integer pageNumber, boolean isActive, Long userId);

    Page<Product> findAllByNameAndActivity(Integer pageNumber, String name, boolean isActive, Long userId);

    void activate(Long id, Long userId);

    Product findById(Long id, Long userId);

    void create(Product product, Long userId);

    void update(Product product, Long userId);

    void delete(Long id, Long userId);

}
