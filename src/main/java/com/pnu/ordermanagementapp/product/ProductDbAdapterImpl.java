package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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

    @Autowired
    public ProductDbAdapterImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Page<Product> findAll(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber-1, 10, Sort.Direction.ASC, "name");
        return productDao.findAll(pageable);
    }

    @Override
    public Page<Product> findAllByName(Integer pageNumber, String name) {
        Pageable pageable = PageRequest.of(pageNumber-1, 10, Sort.Direction.ASC, "name");
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
        productDao.deleteById(id);
    }

}
