package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.exception.ServiceException;
import com.pnu.ordermanagementapp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDbAdapterImpl implements ProductDbAdapter {

    private ProductDao productDao;

    private static int PAGE_SIZE = 10;

    private static Sort SORT = Sort.by("active").descending().and(Sort.by("name").ascending());

    private Pageable pageable;

    @Autowired
    public ProductDbAdapterImpl(ProductDao productDao) {
        this.productDao = productDao;
        this.pageable = PageRequest.of(0, PAGE_SIZE, SORT);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Page<Product> findAll(int pageNumber) {
        return productDao.findAll(PageRequest.of(pageNumber - 1, PAGE_SIZE, SORT));
    }

    @Override
    public Page<Product> findAllByActivity(Integer pageNumber, boolean isActive) {
        pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, SORT);
        int total = productDao.countByActivity(isActive);
        List<Product> products = productDao.findAllByActivity(isActive, pageable.getPageSize(), pageable.getOffset());
        return new PageImpl<>(products, pageable, total);
    }

    @Override
    public Page<Product> findAllByNameAndActivity(Integer pageNumber, String name, boolean isActive) {
        pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, SORT);
        int total = productDao.countByNameAndActivity(name, isActive);
        List<Product> products = productDao.findAllByNameAndActivity(name, isActive, pageable.getPageSize(), pageable.getOffset());
        return new PageImpl<>(products, pageable, total);
    }

    @Override
    public void create(Product product) {
        productDao.save(product);
    }

    @Override
    public void update(Product product) {
        productDao.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productDao.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        Product product = findProductByIdOrThrowException(id);
        product.setActive(false);
        productDao.save(product);
    }

    @Override
    public void activate(Long id) {
        Product product = findProductByIdOrThrowException(id);
        product.setActive(true);
        productDao.save(product);
    }

    private Product findProductByIdOrThrowException(Long id) {
        return productDao.findById(id)
                .orElseThrow(() -> new ServiceException("Product not found!"));
    }
}
