package com.example.shop.service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.example.shop.dto.BillItemDTO;
import com.example.shop.dto.ProductDTO;
import com.example.shop.entity.BillItem;
import com.example.shop.entity.Product;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	SpringTemplateEngine templateEngine;
	
	
	public void sendBill(String to, String name ,List<BillItemDTO> billItems ) {
		String subject = "Đơn hàng của bạn đã đặt thành công!";
		
		Context ctx =new Context();
		ctx.setVariable("name", name);
		ctx.setVariable("billItems", billItems);
		String body = templateEngine.process("sendBill.html", ctx);
		sendMail(to, subject, body);
		
	}
	
	public void sendMail(String to, String subject,String body) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,StandardCharsets.UTF_8.name());
		
		try {
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			
			javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Async
	public void sendMailwithFile(String to, String subject, String body) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
		try {
			helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		File attackment = new File("D:\\JAVAWEB\\project3\\temp.xlsx");
	
		try {
			//load template email with content
			Context context = new Context();
			context.setVariable("name", body);
			String html = templateEngine.process("hi.html", context);
			//send email
			helper.setTo(to); //email gui toi
			helper.setSubject(subject);//noi dung
			helper.setText(html, true);// true : ho tro HTML
			helper.setFrom("namphamvan2001@gmail.com");
			
			//them file neu dinh kem ton tai
			if(attackment != null) {
				System.err.println("da vao duoc day");
				String attachmentName = attackment.getName();
				helper.addAttachment(attachmentName,attackment);
				System.err.println("dong 94");
			}
			
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
