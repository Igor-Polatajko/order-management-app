package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    @Override
    <S extends Product> S save(S s);

    Optional<Product> findByIdAndUserId(Long id, Long userId);

    @Override
    void delete(Product product);

    List<Product> findAllByUserId(Long userId);

    Page<Product> findByActiveAndUserId(boolean active, long userId, Pageable pageable);

    Page<Product> findByActiveAndUserIdAndNameContains(boolean active, long userId, String name, Pageable pageable);
}
