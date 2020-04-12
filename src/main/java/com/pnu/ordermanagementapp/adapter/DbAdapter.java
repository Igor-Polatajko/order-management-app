package com.pnu.ordermanagementapp.adapter;

import java.util.List;

public interface DbAdapter<T> { // added only to meet task requirements

    T findById(String id);

    List<T> findAll();

    void create(T obj);

    void update(T obj);

    void delete(String id);

}
