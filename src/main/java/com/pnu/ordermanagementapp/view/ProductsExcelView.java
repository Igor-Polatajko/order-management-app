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
        createHeader(sheet);
        int rowCount = 1;


        for (Product product : products) {
            Row courseRow = sheet.createRow(rowCount++);
            int idx = 0;
            courseRow.createCell(idx++).setCellValue(product.getId());
            courseRow.createCell(idx++).setCellValue(product.getName());
            courseRow.createCell(idx++).setCellValue(product.getAmount());
            courseRow.createCell(idx++).setCellValue(product.getPrice());
            courseRow.createCell(idx++).setCellValue(String.valueOf(product.isActive()));
        }
    }

    private void createHeader(Sheet sheet) {
        Row header = sheet.createRow(0);
        int idx = 0;
        header.createCell(idx++).setCellValue("ID");
        header.createCell(idx++).setCellValue("Name");
        header.createCell(idx++).setCellValue("Amount");
        header.createCell(idx++).setCellValue("Price");
        header.createCell(idx++).setCellValue("Active");
    }
}
