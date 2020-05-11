package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.exception.ServiceException;
import com.pnu.ordermanagementapp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductDao productDao;

    private static int pageSize = 10;

    private static String sortByAttribute = "name";

    private static Sort.Direction sortDirection = Sort.Direction.ASC;

    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }


    @Override
    public Page<Product> findAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sortDirection, sortByAttribute);
        int total = productDao.countActive();
        List<Product> products = productDao.findAllActive(pageable.getPageSize(), pageable.getOffset());
        return new PageImpl(products, pageable, total);
    }

    @Override
    public Page<Product> findAllByName(Integer pageNumber, String name) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sortDirection, sortByAttribute);
        int total = productDao.countWithName(name);
        List<Product> products = productDao.findAllByName(name, pageable.getPageSize(), pageable.getOffset());
        Page<Product> page = new PageImpl(products, pageable, total);
        return page;
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

    private Product findProductByIdOrThrowException(Long id) {
        return productDao.findById(id)
                .orElseThrow(() -> new ServiceException("Product not found!"));
    }
}
