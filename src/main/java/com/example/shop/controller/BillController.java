package com.example.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.dto.BestUser;
import com.example.shop.dto.BestsellingProduct;
import com.example.shop.dto.BillDTO;
import com.example.shop.dto.BillStatisticDTO;
import com.example.shop.dto.BillStatisticDTO2;
import com.example.shop.dto.OrdersByBuyerName;
import com.example.shop.dto.PageDTO;
import com.example.shop.dto.ResponseDTO;
import com.example.shop.service.BillService;
import com.example.shop.service.EmailService;

@RestController
@RequestMapping("/bill")
public class BillController {
	@Autowired // DI: dependency inject
	BillService billService;
	
	@Autowired
	EmailService emailService;
	

	@PostMapping("/") // gia su: khong upload file
	public ResponseDTO<Void> create(@RequestBody BillDTO billDTO) {
		billService.create(billDTO);
		emailService.sendBill(billDTO.getUser().getEmail(),billDTO.getUser().getName() , billDTO.getBillItems());
	
		return ResponseDTO.<Void>builder().status(200).message("Create Ok").build();
	}
	@PutMapping("/") // gia su: khong upload file
	public ResponseDTO<Void> update(@RequestBody BillDTO billDTO) {
		billService.update(billDTO);
		return ResponseDTO.<Void>builder().status(200).message("Update Ok").build();
	}
	@DeleteMapping("/{id}") // gia su: khong upload file
	public ResponseDTO<Void> delete(@PathVariable("id") int id ,BillDTO billDTO) {
		billService.delete(id);
		return ResponseDTO.<Void>builder().status(200).message("DELETE Ok").build();
	}
	@GetMapping("/")
	public ResponseDTO<BillDTO> get(@RequestParam("id") int id) {
		return ResponseDTO.<BillDTO>builder().status(200).data(billService.getById(id)).build();
	}
	@GetMapping("/statistic")
	public ResponseDTO<PageDTO<BillStatisticDTO>> statistic() {
		PageDTO<BillStatisticDTO> pageDTO = billService.statistic();
		return ResponseDTO.<PageDTO<BillStatisticDTO>>builder().status(200).data(pageDTO).build();
	}
	@GetMapping("/statistic2")
	public ResponseDTO<PageDTO<BillStatisticDTO2>> statistic2() {
		PageDTO<BillStatisticDTO2> pageDTO = billService.statistic2();
		return ResponseDTO.<PageDTO<BillStatisticDTO2>>builder().status(200).data(pageDTO).build();
	}
	@GetMapping("/bestselling-produc")
	public ResponseDTO<List<BestsellingProduct>> bestsellingProduct() {
		List<BestsellingProduct> bestsellingProducts= billService.getBestsellingProducts();
		return ResponseDTO.<List<BestsellingProduct>>builder().status(200).data(bestsellingProducts).build();
	}
	@GetMapping("/best-user")
	public ResponseDTO<List<BestUser>> bestUser() {
		List<BestUser> bestUsers= billService.bestUser();
		return ResponseDTO.<List<BestUser>>builder().status(200).data(bestUsers).build();
	}
	
	@GetMapping("/search-date")
	public ResponseDTO<List<BillDTO>> searchByDate() {
		List<BillDTO> lisBills= billService.searchByDate();
		if(lisBills.isEmpty()) {
			System.out.println("aaaaaaaaaaaaaaaaaaa");
			
		}
		return ResponseDTO.<List<BillDTO>>builder().status(200).data(lisBills).build();
	}
	@GetMapping("/order-user")
	public ResponseDTO<List<OrdersByBuyerName>> countOrdersByBuyerName() {
		List<OrdersByBuyerName> list= billService.countOrdersByBuyerName();
		return ResponseDTO.<List<OrdersByBuyerName>>builder().status(200).data(list).build();
	}
}
