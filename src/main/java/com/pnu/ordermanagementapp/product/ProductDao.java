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

public interface ProductDao extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    @Override
    List<Product> findAll(Sort sort);

    @Override
    Page<Product> findAll(Pageable pageable);

    @Query(value = "Select * from products p where p.active = 1 ORDER BY name LIMIT ?1 OFFSET ?2",
            nativeQuery = true)
    List<Product> findAllActive(int limit, long offset);

    @Query("Select count(p) from Product p where p.active = true")
    int countActive();

    @Override
    <S extends Product> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    <S extends Product> S save(S s);

    @Override
    Optional<Product> findById(Long aLong);

    @Query(value = "Select * from products p where p.active = 1 and p.name LIKE %?1% ORDER BY name LIMIT ?2 OFFSET ?3",
            nativeQuery = true)
    List<Product> findAllByName(String name, int limit, long offset);

    @Query("Select count(p) from Product p  where p.active = true and p.name LIKE  %?1%")
    int countWithName(String name);

}
