package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.exception.ServiceException;
import com.pnu.ordermanagementapp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    private static final int PAGE_SIZE = 10;

    private static final Sort SORT = Sort.by("active").descending().and(Sort.by("name").ascending());

    private ProductRepository productRepository;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> findAll(Long userId) {
        return productRepository.findAllByUserId(userId);
    }

    @Override
    public Page<Product> findAllByActivity(Integer pageNumber, boolean isActive, Long userId) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, SORT);
//        int total = productRepository.countByActivity(isActive);
//        List<Product> products = productRepository.findAllByActivity(isActive, pageable.getPageSize(), pageable.getOffset());
//        return new PageImpl<>(products, pageable, total);
        return productRepository.findByActiveAndUserId(isActive, userId, pageable);
    }

    @Override
    public Page<Product> findAllByNameAndActivity(Integer pageNumber, String name, boolean isActive, Long userId) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, SORT);
//        int total = productRepository.countByNameAndActivity(name, isActive);
//        List<Product> products = productRepository.findAllByNameAndActivity(name, isActive, pageable.getPageSize(), pageable.getOffset());
//        return new PageImpl<>(products, pageable, total);
        return productRepository.findByActiveAndUserIdAndNameContains(isActive, userId, name, pageable);
    }

    @Override
    public void create(Product product, Long userId) {
        product.setUserId(userId);
        productRepository.save(product);
    }

    @Override
    public void update(Product product, Long userId) {
        if (product.getUserId().equals(userId)) {
            productRepository.save(product);
        } else {
            throw new ServiceException("Not allowed! Product id fail");
        }
    }

    @Override
    public Product findById(Long id, Long userId) {
        return productRepository.findByIdAndUserId(id, userId).get();
    }

    @Override
    public void delete(Long id, Long userId) {
        Product product = findProductByIdOrThrowException(id);
        if (product.getUserId().equals(userId)) {
            if (product.isActive()) {
                product.setActive(false);
                productRepository.save(product);
            } else {
                productRepository.delete(product);
            }

        } else {
            throw new ServiceException("Not allowed! Product id fail");
        }
    }

    @Override
    public void activate(Long id, Long userId) {
        Product product = findProductByIdOrThrowException(id);
        if (product.getUserId().equals(userId)) {
            product.setActive(true);
            productRepository.save(product);
        } else {
            throw new ServiceException("Not allowed! Product id fail");
        }
    }

    private Product findProductByIdOrThrowException(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Product not found!"));
    }
}
