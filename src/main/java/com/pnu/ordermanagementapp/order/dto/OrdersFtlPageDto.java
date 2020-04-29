package com.pnu.ordermanagementapp.order.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrdersFtlPageDto {

    private List<OrderFtlDto> content;

    private int currentPageNumber;

    private int totalPageNumber;

    private boolean hasNextPage;

    private boolean hasPreviousPage;

}
