package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Page<Product> findAllByName(Integer pageNumber, String name, Long userId);

    List<Product> findAll(Long userId);

    Page<Product> findAll(int pageNumber, Long userId);

    Product findById(Long id, Long userId);

    void create(Product product, Long userId);

    void update(Product product, Long userId);

    void delete(Long id, Long userId);

}
