package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.exception.ServiceException;
import com.pnu.ordermanagementapp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        return productRepository.findByActiveAndUserId(isActive, userId, pageable);
    }

    @Override
    public Page<Product> findAllByNameAndActivity(Integer pageNumber, String name, boolean isActive, Long userId) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, SORT);
        return productRepository.findByActiveAndUserIdAndNameContains(isActive, userId, name, pageable);
    }

    @Override
    public void create(Product product, Long userId) {
        product = product.toBuilder().userId(userId).build();
        productRepository.save(product);
    }

    @Override
    public void update(Product product, Long userId) {
        findProductByIdOrThrowException(product.getId(), userId);
        productRepository.save(product);
    }

    @Override
    public Product findById(Long id, Long userId) {
        return productRepository.findByIdAndUserId(id, userId).get();
    }

    @Override
    public void delete(Long id, Long userId) {
        Product product = findProductByIdOrThrowException(id, userId);
        if (product.isActive()) {
            product = product.toBuilder().active(false).build();
            productRepository.save(product);
        } else {
            productRepository.delete(product);
        }

    }

    @Override
    public void activate(Long id, Long userId) {
        Product product = findProductByIdOrThrowException(id, userId);
        product = product.toBuilder().active(true).build();
        productRepository.save(product);
    }

    private Product findProductByIdOrThrowException(Long id, Long userId) {
        return productRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ServiceException("Product not found!"));
    }
}
