package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Page<Product> findAllByName(Integer pageNumber, String name);

    List<Product> findAll();

    Page<Product> findAll(int pageNumber);

    Product findById(Long id);

    void create(Product obj);

    void update(Product obj);

    void delete(Long id);

}
