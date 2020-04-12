package com.pnu.ordermanagementapp.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int amount;

    private double price;

    @OneToMany(mappedBy = "product")
    private List<Order> orders;

}
