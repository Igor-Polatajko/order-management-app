package com.pnu.ordermanagementapp.view;

import com.pnu.ordermanagementapp.dto.order.OrderFtlDto;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component("ordersExcelView")
public class OrdersExcelView extends AbstractXlsView {

    private final static String FILE_NAME = "orders.xls";

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + FILE_NAME);


        List<OrderFtlDto> orders = (List<OrderFtlDto>) model.get("orders");
        Sheet sheet = workbook.createSheet("Orders");

        createHeader(sheet);

        int rowCount = 1;
        for (OrderFtlDto order : orders) {
            Row courseRow = sheet.createRow(rowCount++);

            int idx = 0;
            courseRow.createCell(idx++).setCellValue(order.getOrderId());
            courseRow.createCell(idx++).setCellValue(order.getState());
            courseRow.createCell(idx++).setCellValue(order.getCreatedDateTime());
            courseRow.createCell(idx++).setCellValue(order.getUpdatedDateTime());
            courseRow.createCell(idx++).setCellValue(order.getClientFirstName());
            courseRow.createCell(idx++).setCellValue(order.getClientLastName());
            courseRow.createCell(idx++).setCellValue(order.getClientEmail());
            courseRow.createCell(idx++).setCellValue(order.getProductName());
            courseRow.createCell(idx++).setCellValue(order.getItemPrice());
            courseRow.createCell(idx++).setCellValue(order.getProductOrderAmount());
            courseRow.createCell(idx).setCellValue(order.getTotalPrice());
        }
    }

    private void createHeader(Sheet sheet) {
        Row header = sheet.createRow(0);

        int idx = 0;
        header.createCell(idx++).setCellValue("ID");
        header.createCell(idx++).setCellValue("State");
        header.createCell(idx++).setCellValue("Created time");
        header.createCell(idx++).setCellValue("Updated time");
        header.createCell(idx++).setCellValue("Client first name");
        header.createCell(idx++).setCellValue("Client last name");
        header.createCell(idx++).setCellValue("Client email");
        header.createCell(idx++).setCellValue("Product name");
        header.createCell(idx++).setCellValue("Item price");
        header.createCell(idx++).setCellValue("Amount");
        header.createCell(idx).setCellValue("Total price");
    }
}
