package com.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.service.StatisticService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class StatisticController {
	@Autowired
	StatisticService statisticService;
	
	@GetMapping("/excel")
	public void generateXecelReport(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		String headerKey="Content-Disposition";
		String headerValue="attachment;filename=Tkbill.xls";
		
		response.setHeader(headerKey, headerValue);
		statisticService.generateExcel(response);
	}
}
