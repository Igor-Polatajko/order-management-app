package com.pnu.ordermanagementapp.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private String name;

    private int amount;

    private double price;

}
