package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    @Override
    Page<Product> findAll(Pageable pageable);

    @Query(value = "Select * from products p where p.active = ?1 ORDER BY name LIMIT ?2 OFFSET ?3",
            nativeQuery = true)
    List<Product> findAllByActivity(boolean isActive, int limit, long offset);

    @Query("Select count(p) from Product p where p.active = ?1")
    int countByActivity(boolean isActive);

    @Query(value = "Select * from products p where p.active = ?2 and p.name LIKE %?1% ORDER BY name LIMIT ?3 OFFSET ?4",
            nativeQuery = true)
    List<Product> findAllByNameAndActivity(String name, boolean isActive, int limit, long offset);

    @Query("Select count(p) from Product p  where p.active = ?2 and p.name LIKE %?1%")
    int countByNameAndActivity(String name, boolean isActive);

    @Override
    <S extends Product> S save(S s);

    @Override
    Optional<Product> findById(Long aLong);

    @Override
    void delete(Product product);
}
