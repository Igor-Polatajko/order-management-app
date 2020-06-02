package com.pnu.ordermanagementapp.service;

import com.pnu.ordermanagementapp.dto.product.ProductFormSubmitDto;
import com.pnu.ordermanagementapp.exception.ServiceException;
import com.pnu.ordermanagementapp.model.Product;
import com.pnu.ordermanagementapp.repository.ProductRepository;
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
        return productRepository.findByUserId(userId);
    }

    @Override
    public List<Product> findAllActive(Long userId) {
        return productRepository.findByActiveAndUserId(true, userId);
    }

    @Override
    public Page<Product> findAllByActivity(Integer pageNumber, boolean isActive, Long userId) {
        Pageable pageable = createPageable(pageNumber);
        return productRepository.findByActiveAndUserId(isActive, userId, pageable);
    }

    @Override
    public Page<Product> findAllByNameAndActivity(Integer pageNumber, String name, boolean isActive, Long userId) {
        Pageable pageable = createPageable(pageNumber);
        return productRepository.findByActiveAndUserIdAndNameContains(isActive, userId, name, pageable);
    }

    @Override
    public void create(ProductFormSubmitDto productFormDto, Long userId) {

        Product product = Product.builder()
                .name(productFormDto.getName())
                .price(productFormDto.getPrice())
                .amount(productFormDto.getAmount())
                .build();

        if (productRepository.existsProductByNameAndUserId(product.getName(), userId)) {
            throw new ServiceException(String.format(
                    "Product with name '%s' already exists (active?: %s)", product.getName(), product.isActive()
            ));
        }

        product = product.toBuilder()
                .userId(userId)
                .active(true)
                .build();

        productRepository.save(product);
    }

    @Override
    public void update(ProductFormSubmitDto productFormDto, Long userId) {

        Product product = Product.builder()
                .id(productFormDto.getId())
                .name(productFormDto.getName())
                .price(productFormDto.getPrice())
                .amount(productFormDto.getAmount())
                .build();

        if (productRepository.existsProductByNameAndUserIdAndIdNot(
                product.getName(), userId, product.getId())) {
            throw new ServiceException(String.format(
                    "Product with name '%s' already exists (active?: %s)", product.getName(), product.isActive()
            ));
        }

        Product productFromDb = findProductByIdOrThrowException(product.getId(), userId);

        productFromDb = productFromDb.toBuilder()
                .name(product.getName())
                .amount(product.getAmount())
                .price(product.getPrice())
                .build();

        productRepository.save(productFromDb);
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

    private Pageable createPageable(int pageNumber) {

        if (pageNumber < 1) {
            throw new ServiceException("Incorrect page number!");
        }

        return PageRequest.of(pageNumber - 1, PAGE_SIZE, SORT);
    }
}
