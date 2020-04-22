package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.model.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends JpaRepository<Product, Long> {

    @Override
    List<Product> findAll(Sort sort);

    @Override
    <S extends Product> S save(S s);

    @Override
    Optional<Product> findById(Long aLong);

    @Override
    void deleteById(Long aLong);

    @Query("Select p from Product p where p.name LIKE  %?1%")
    List<Product> findByNameContainingIgnoreCase(String name);
}
