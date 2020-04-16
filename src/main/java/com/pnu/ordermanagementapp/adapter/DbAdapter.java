package com.pnu.ordermanagementapp.adapter;

import java.util.List;

public interface DbAdapter<T> { // added only to meet task requirements

    List<T> findAll();

    T findById(Long id);

    void create(T obj);

    void update(T obj);

    void delete(Long id);
}
