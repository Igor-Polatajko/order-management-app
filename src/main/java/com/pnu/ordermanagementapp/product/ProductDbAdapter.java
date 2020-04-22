package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.adapter.DbAdapter;
import com.pnu.ordermanagementapp.model.Product;
import org.springframework.data.domain.Page;

public interface ProductDbAdapter extends DbAdapter<Product> {

    Page<Product> findAll(Integer page);

    Page<Product> findAllByName(Integer page, String name);

}
