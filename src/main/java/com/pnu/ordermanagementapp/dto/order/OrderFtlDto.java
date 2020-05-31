package com.pnu.ordermanagementapp.dto.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderFtlDto {

    private long orderId;

    private String productName;

    private String clientFirstName;

    private String clientLastName;

    private String clientEmail;

    private double itemPrice;

    private String state;

    private boolean active;

    private int productOrderAmount;

    private double totalPrice;

    private String createdDate;

}
