package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.adapter.DbAdapter;
import com.pnu.ordermanagementapp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDbAdapter implements DbAdapter<Product> {

    private ProductDao productDao;

    @Autowired
    public ProductDbAdapter(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    public List<Product> findAllLike(String name) {
        return productDao.findByNameContainingIgnoreCase(name);

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
