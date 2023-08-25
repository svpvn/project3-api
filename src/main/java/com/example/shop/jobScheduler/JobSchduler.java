package com.example.shop.jobScheduler;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.shop.entity.Bill;
import com.example.shop.repository.BillRepo;
import com.example.shop.service.EmailService;

import jakarta.transaction.Transactional;

@Component
public class JobSchduler {
	@Autowired
	BillRepo billRepo;
	
	@Autowired 
	EmailService emailService;
	
	@Scheduled(fixedDelay = 1000 * 60 * 60)
	@Transactional
	public void sendAdminEmail() {
		//lay gio hien tai
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.MINUTE, -2);
		Date date = calendar.getTime();
		
		List<Bill> bills = billRepo.searchByDate(date);
		for(Bill b : bills) {
			emailService.sendMail("namphamvan2001@gmail.com", "Đơn hàng mới từ "+b.getUser().getName(), "aa");
		}
	}
	public void sendMailExcel(String eamailUser) {
		emailService.sendMailwithFile(eamailUser, "file thong ke bill", "abc");
	}
	
}
