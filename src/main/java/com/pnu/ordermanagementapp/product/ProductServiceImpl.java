package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.exception.ServiceException;
import com.pnu.ordermanagementapp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private static int pageSize = 10;

    private static String sortByName = "name";

    private static Sort.Direction sortDirection = Sort.Direction.ASC;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll(Long userId) {
        return productRepository.findAllByUserId(userId);
    }

    @Override
    public Page<Product> findAll(int pageNumber, Long userId) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sortDirection, sortByName);
        int total = productRepository.countActiveByUserId(userId);
        List<Product> products = productRepository
                .findAllActiveByUserId(userId, pageable.getPageSize(), pageable.getOffset());

        return new PageImpl<>(products, pageable, total);
    }

    @Override
    public Page<Product> findAllByName(Integer pageNumber, String name, Long userId) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sortDirection, sortByName);
        int total = productRepository.countWithNameAndUserId(name, userId);
        List<Product> products = productRepository
                .findAllByNameAndUserId(name, userId, pageable.getPageSize(), pageable.getOffset());

        return new PageImpl<>(products, pageable, total);
    }

    @Override
    public void create(Product product, Long userId) {
        Product productWithUserId = product.toBuilder()
                .userId(userId)
                .build();

        productRepository.save(productWithUserId);
    }

    @Override
    public void update(Product product, Long userId) {
        findProductByIdOrThrowException(product.getId(), userId);
        productRepository.save(product);
    }

    @Override
    public Product findById(Long id, Long userId) {
        return findProductByIdOrThrowException(id, userId);
    }

    @Override
    public void delete(Long id, Long userId) {
        Product product = findProductByIdOrThrowException(id, userId);
        product.setActive(false);
        productRepository.save(product);
    }

    private Product findProductByIdOrThrowException(Long id, Long userId) {
        return productRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ServiceException("Product not found!"));
    }
}
