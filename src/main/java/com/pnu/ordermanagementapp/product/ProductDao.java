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

    @Query(value = "Select * from products p where p.name LIKE %?1% ORDER BY name LIMIT ?2 OFFSET ?3",
            nativeQuery = true)
    List<Product> findAllByName(String name, int limit, long offset);

    @Query("Select count(p) from Product p where p.name LIKE  %?1%")
    int countByName(String name);

    @Query(value = "Select * from products p where p.active = 1 ORDER BY name LIMIT ?1 OFFSET ?2",
            nativeQuery = true)
    List<Product> findAllActive(int limit, long offset);

    @Query("Select count(p) from Product p where p.active = true")
    int countActive();

    @Query(value = "Select * from products p where p.active = 1 and p.name LIKE %?1% ORDER BY name LIMIT ?2 OFFSET ?3",
            nativeQuery = true)
    List<Product> findAllActiveByName(String name, int limit, long offset);

    @Query("Select count(p) from Product p  where p.active = true and p.name LIKE  %?1%")
    int countActiveByName(String name);

    @Query(value = "Select * from products p where p.active = 0 ORDER BY name LIMIT ?1 OFFSET ?2",
            nativeQuery = true)
    List<Product> findAllArchived(int limit, long offset);

    @Query("Select count(p) from Product p where p.active = false")
    int countArchived();

    @Query(value = "Select * from products p where p.active = 0 and p.name LIKE %?1% ORDER BY name LIMIT ?2 OFFSET ?3",
            nativeQuery = true)
    List<Product> findAllArchivedByName(String name, int limit, long offset);

    @Query("Select count(p) from Product p  where p.active = false and p.name LIKE %?1%")
    int countArchivedByName(String name);

    @Override
    <S extends Product> S save(S s);

    @Override
    Optional<Product> findById(Long aLong);

}
