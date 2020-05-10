package com.pnu.ordermanagementapp.adapter;

import org.springframework.data.domain.Page;

import java.util.List;

// ToDo remove this at all
public interface DbAdapter<T> { // added only to meet task requirements

    List<T> findAll();

    Page<T> findAll(int pageNumber);

    T findById(Long id);

    void create(T obj);

    void update(T obj);

    void delete(Long id);
}
