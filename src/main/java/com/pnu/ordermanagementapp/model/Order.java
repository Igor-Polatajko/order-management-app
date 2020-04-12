package com.pnu.ordermanagementapp.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Order {

    private Product product;

    private Client client;

    private LocalDateTime createdDate;

}
