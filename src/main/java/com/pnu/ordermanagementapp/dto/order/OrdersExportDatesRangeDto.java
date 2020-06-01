package com.pnu.ordermanagementapp.dto.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrdersExportDatesRangeDto {

    private String startDate;

    private String endDate;

}
