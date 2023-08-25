package com.example.shop.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.dto.BillStatisticDTO2;
import com.example.shop.entity.Bill;
import com.example.shop.repository.BillRepo;
import com.example.shop.service.BillService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class StatisticService {
	@Autowired
	BillRepo billRepo;
	
	public void generateExcel(HttpServletResponse response) throws Exception {
		List<BillStatisticDTO2> bills = billRepo.thongKeBill2();
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet= workbook.createSheet("ThongKe");
		HSSFRow row =  sheet.createRow(0);
		row.createCell(0).setCellValue("Total");
		row.createCell(1).setCellValue("Thang");
		row.createCell(2).setCellValue("Nam");	
		
		int dataRowIndex = 1 ;
				
		for(BillStatisticDTO2 bill: bills) {
			HSSFRow dataRow =  sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(bill.getQuantity());
			dataRow.createCell(1).setCellValue(bill.getMonth());
			dataRow.createCell(2).setCellValue(bill.getYear());
			dataRowIndex++;
		}
		
		ServletOutputStream ops=	response.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();
	}
	
}
