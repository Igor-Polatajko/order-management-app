package com.pnu.ordermanagementapp.product;

import com.pnu.ordermanagementapp.adapter.DbAdapter;
import com.pnu.ordermanagementapp.model.Product;
import org.springframework.data.domain.Page;

public interface ProductDbAdapter extends DbAdapter<Product> {

    Page<Product> findAllByActivity(Integer pageNumber, boolean isActive);

    Page<Product> findAllByNameAndActivity(Integer pageNumber, String name, boolean isActive);

    void activate(Long id);


}
