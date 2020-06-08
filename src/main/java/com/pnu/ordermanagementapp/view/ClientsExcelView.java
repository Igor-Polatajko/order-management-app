package com.pnu.ordermanagementapp.view;

import com.pnu.ordermanagementapp.model.Client;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@Component("clientsExcelView")
public class ClientsExcelView extends AbstractXlsView {

    private final static String FILE_NAME = "clients.xls";

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + FILE_NAME);

        List<Client> clients = (List<Client>) model.get("clients");
        Sheet sheet = workbook.createSheet("Clients");
        createHeader(sheet);
        int rowCount = 1;

        for (Client client : clients) {
            Row courseRow = sheet.createRow(rowCount++);

            int idx = 0;
            courseRow.createCell(idx++).setCellValue(client.getId());
            courseRow.createCell(idx++).setCellValue(client.getFirstName());
            courseRow.createCell(idx++).setCellValue(client.getLastName());
            courseRow.createCell(idx++).setCellValue(client.getEmail());
            courseRow.createCell(idx++).setCellValue(String.valueOf(client.isActive()));
        }
    }

    private void createHeader(Sheet sheet) {
        Row header = sheet.createRow(0);
        int idx = 0;
        header.createCell(idx++).setCellValue("ID");
        header.createCell(idx++).setCellValue("First name");
        header.createCell(idx++).setCellValue("Last name");
        header.createCell(idx++).setCellValue("Email");
        header.createCell(idx++).setCellValue("Active");

    }
}
