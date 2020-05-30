package com.pnu.ordermanagementapp.view;

import com.pnu.ordermanagementapp.model.Product;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component("productsExcelView")
public class ProductsExcelView extends AbstractXlsView {

    private final static String FILE_NAME = "products.xls";

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + FILE_NAME);

        List<Product> products = (List<Product>) model.get("products");

        Sheet sheet = workbook.createSheet("Products");

        int rowCount = 0;

        Row header = sheet.createRow(rowCount++);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Amount");
        header.createCell(3).setCellValue("Price");

        for (Product product : products) {
            Row courseRow = sheet.createRow(rowCount++);
            courseRow.createCell(0).setCellValue(product.getId());
            courseRow.createCell(1).setCellValue(product.getName());
            courseRow.createCell(2).setCellValue(product.getAmount());
            courseRow.createCell(3).setCellValue(product.getPrice());
        }
    }
}
