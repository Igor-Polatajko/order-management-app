package com.pnu.ordermanagementapp.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {

    private long orderId;

    private String productName;

    private String clientFirstName;

    private String clientLastName;

    private String clientEmail;

    private double itemPrice;

    private int productOrderAmount;

    private double totalPrice;

    private String createdDate;

}
