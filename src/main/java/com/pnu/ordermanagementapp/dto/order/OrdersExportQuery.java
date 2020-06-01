package com.pnu.ordermanagementapp.dto.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrdersExportQuery {

    private OrdersExportDatesRangeDto ordersExportDatesRangeDto;

    private Long clientId;

    private Long productId;

    private Long userId;

}
