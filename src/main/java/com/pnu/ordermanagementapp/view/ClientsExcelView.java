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
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename="+FILE_NAME);

        List<Client> clients = (List<Client>) model.get("clients");
        Sheet sheet = workbook.createSheet("Clients");

        int rowCount = 0;

        Row header = sheet.createRow(rowCount++);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("First name");
        header.createCell(2).setCellValue("Last name");
        header.createCell(3).setCellValue("Email");

        for (Client client : clients) {
            Row courseRow = sheet.createRow(rowCount++);
            courseRow.createCell(0).setCellValue(client.getId());
            courseRow.createCell(1).setCellValue(client.getFirstName());
            courseRow.createCell(2).setCellValue(client.getLastName());
            courseRow.createCell(3).setCellValue(client.getEmail());
        }
    }
}
