package com.pnu.ordermanagementapp.view;

import com.pnu.ordermanagementapp.dto.order.OrderFtlDto;
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
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + FILE_NAME);


        List<OrderFtlDto> orders = (List<OrderFtlDto>) model.get("orders");
        Sheet sheet = workbook.createSheet("Orders");

        int rowCount = 0;

        Row header = sheet.createRow(rowCount++);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("State");
        header.createCell(2).setCellValue("Date");
        header.createCell(3).setCellValue("Client first name");
        header.createCell(4).setCellValue("Client last name");
        header.createCell(5).setCellValue("Client email");
        header.createCell(6).setCellValue("Product name");
        header.createCell(7).setCellValue("Item price");
        header.createCell(8).setCellValue("Amount");
        header.createCell(9).setCellValue("Total price");

        for (OrderFtlDto order : orders) {
            Row courseRow = sheet.createRow(rowCount++);
            courseRow.createCell(0).setCellValue(order.getOrderId());
            courseRow.createCell(1).setCellValue(order.getState());
            courseRow.createCell(2).setCellValue(order.getCreatedDate());
            courseRow.createCell(3).setCellValue(order.getClientFirstName());
            courseRow.createCell(4).setCellValue(order.getClientLastName());
            courseRow.createCell(5).setCellValue(order.getClientEmail());
            courseRow.createCell(6).setCellValue(order.getProductName());
            courseRow.createCell(7).setCellValue(order.getItemPrice());
            courseRow.createCell(8).setCellValue(order.getProductOrderAmount());
            courseRow.createCell(9).setCellValue(order.getTotalPrice());

        }
    }
}
