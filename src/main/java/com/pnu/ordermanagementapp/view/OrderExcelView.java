package com.pnu.ordermanagementapp.view;

import com.pnu.ordermanagementapp.dto.order.OrderFtlDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class OrderExcelView extends AbstractXlsView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        List<OrderFtlDto> orders = (List<OrderFtlDto>) model.get("orders");
        Sheet sheet = workbook.createSheet("Orders");

        int rowCount = 0;

        Row header = sheet.createRow(rowCount++);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Date");
        header.createCell(2).setCellValue("Client first name");
        header.createCell(3).setCellValue("Client last name");
        header.createCell(4).setCellValue("Client email");
        header.createCell(5).setCellValue("Product name");
        header.createCell(6).setCellValue("Item price");
        header.createCell(7).setCellValue("Amount");
        header.createCell(8).setCellValue("Total price");

        for (OrderFtlDto order : orders) {
            Row courseRow = sheet.createRow(rowCount++);
            courseRow.createCell(0).setCellValue(order.getOrderId());
            courseRow.createCell(1).setCellValue(order.getCreatedDate());
            courseRow.createCell(2).setCellValue(order.getClientFirstName());
            courseRow.createCell(3).setCellValue(order.getClientLastName());
            courseRow.createCell(4).setCellValue(order.getClientEmail());
            courseRow.createCell(5).setCellValue(order.getProductName());
            courseRow.createCell(6).setCellValue(order.getItemPrice());
            courseRow.createCell(7).setCellValue(order.getProductOrderAmount());
            courseRow.createCell(8).setCellValue(order.getTotalPrice());

        }
    }
}