package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.model.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    List<Product> findAllByUserId(Long userId);

    @Query(value = "Select * from products p where p.active = 1 and p.user_id = ?1 ORDER BY name LIMIT ?2 OFFSET ?3",
            nativeQuery = true)
    List<Product> findAllActiveByUserId(Long userId, int limit, long offset);

    @Query("Select count(p) from Product p where p.active = true and p.userId = ?1")
    int countActiveByUserId(Long userId);

    <S extends Product> Page<S> findAllByUserId(Example<S> example, Long userId, Pageable pageable);

    <S extends Product> S save(S s);

    Optional<Product> findByIdAndUserId(Long id, Long userId);

    @Query(value = "Select * from products p where p.active = 1 " +
            "and p.name LIKE %?1% and p.user_id = ?2 ORDER BY name LIMIT ?3 OFFSET ?4",
            nativeQuery = true)
    List<Product> findAllByNameAndUserId(String name, Long userId, int limit, long offset);

    @Query("Select count(p) from Product p where p.active = true and p.userId = ?2 and p.name LIKE  %?1%")
    int countWithNameAndUserId(String name, Long userId);

}
