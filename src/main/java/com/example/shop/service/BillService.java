package com.example.shop.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shop.dto.BestUser;
import com.example.shop.dto.BestsellingProduct;
import com.example.shop.dto.BillDTO;
import com.example.shop.dto.BillItemDTO;
import com.example.shop.dto.BillStatisticDTO;
import com.example.shop.dto.BillStatisticDTO2;
import com.example.shop.dto.OrdersByBuyerName;
import com.example.shop.dto.PageDTO;
import com.example.shop.entity.Bill;
import com.example.shop.entity.BillItem;
import com.example.shop.entity.Coupon;
import com.example.shop.entity.User;
import com.example.shop.jobScheduler.JobSchduler;
import com.example.shop.repository.BillRepo;
import com.example.shop.repository.CouponRepo;
import com.example.shop.repository.ProductRepo;
import com.example.shop.repository.UserRepo;

import jakarta.persistence.NoResultException;

public interface BillService {
	void create(BillDTO billDTO);

	void update(BillDTO billDTO);

	void delete(int id);

	BillDTO getById(int id);

	PageDTO<BillStatisticDTO> statistic();

	PageDTO<BillStatisticDTO2> statistic2();

	List<BillDTO> searchByDate();

	List<BestsellingProduct> getBestsellingProducts();

	List<BestUser> bestUser();

	List<OrdersByBuyerName> countOrdersByBuyerName();

}

@Service
class BillServiceImpl implements BillService {
	@Autowired
	BillRepo billRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	ProductRepo productRepo;

	@Autowired
	CouponRepo couponRepo;

	@Autowired
	JobSchduler jobScheduler;

	@Override
	@Transactional
	public void create(BillDTO billDTO) {
//		Bill bill = new ModelMapper().map(billDTO, Bill.class);
//		billRepo.save(bill);

		Coupon coupon = null;
		User user = userRepo.findById(billDTO.getUser().getId()).orElseThrow(NoResultException::new);
		Bill bill = new Bill();
		if (billDTO.getCouponCode() != null) {
			Calendar calendar = Calendar.getInstance();
			Date dnow = calendar.getTime();
			coupon = couponRepo.findByCoupon(billDTO.getCouponCode(), dnow);
		}
		if (coupon != null) {
			bill.setDiscount(coupon.getDiscountAmount());
			bill.setCouponCode(coupon.getCouponCode());
		} else {
			bill.setDiscount(0);
			bill.setCouponCode("");
		}

		bill.setStatus(billDTO.getStatus());

		bill.setUser(user);

		List<BillItem> billItems = new ArrayList<>();

		for (BillItemDTO billItemDTO : billDTO.getBillItems()) {
			BillItem billItem = new BillItem();
			billItem.setBill(bill);
			billItem.setProduct(
					productRepo.findById(billItemDTO.getProduct().getId()).orElseThrow(NoResultException::new));
			if (coupon != null) {
				billItem.setPrice(billItemDTO.getPrice() - bill.getDiscount());
			} else {
				billItem.setPrice(billItemDTO.getPrice());
			}
			billItem.setQuantity(billItemDTO.getQuantity());

			billItems.add(billItem);
		}

		bill.setBillItems(billItems);
		billRepo.save(bill);
		billDTO.getUser().setName(bill.getUser().getName());
		billDTO.getUser().setEmail(bill.getUser().getEmail());

	}

	@Override
	@CacheEvict(cacheNames = "getbill", key = "#id")
	public void update(BillDTO billDTO) {
		User user = userRepo.findById(billDTO.getUser().getId()).orElseThrow(NoResultException::new);

		Bill bill = billRepo.findById(billDTO.getId()).orElseThrow(NoResultException::new);

		bill.setUser(user);
		billRepo.save(bill);
	}

	@Override
	@CacheEvict(cacheNames = "getbill", key = "#id")
	public void delete(int id) {
		// TODO Auto-generated method stub
		billRepo.deleteById(id);
	}

	@Override
	@Cacheable(cacheNames = "getbill", key = "#id", unless = "#result == null")
	public BillDTO getById(int id) {
		Bill bill = billRepo.findById(id).orElseThrow(NoResultException::new);// java8 lambda

		return new ModelMapper().map(bill, BillDTO.class);
	}

	@Override
	public PageDTO<BillStatisticDTO> statistic() {
		List<Object[]> list = billRepo.thongKeBill();

		PageDTO<BillStatisticDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(1);
		pageDTO.setTotalElements(list.size());

		List<BillStatisticDTO> billStatisticDTOs = new ArrayList<>();
		for (Object[] arr : list) {
			BillStatisticDTO billStatisticDTO = new BillStatisticDTO((long) arr[0],
					String.valueOf(arr[1]) + "/" + String.valueOf(arr[2]));
			billStatisticDTOs.add(billStatisticDTO);
		}
		pageDTO.setData(billStatisticDTOs);
		return pageDTO;
	}

	@Override
	public PageDTO<BillStatisticDTO2> statistic2() {
		List<BillStatisticDTO2> list = billRepo.thongKeBill2();
		PageDTO<BillStatisticDTO2> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(1);
		pageDTO.setTotalElements(list.size());
		pageDTO.setData(list);
		return pageDTO;
	}

	@Override
	public List<BestsellingProduct> getBestsellingProducts() {

		return billRepo.getBestsellingProducts();
	}

	@Override
	public List<BestUser> bestUser() {
		return billRepo.bestUser();
	}

	@Override
	public List<BillDTO> searchByDate() {
		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.DAY_OF_MONTH,);
		Date date = calendar.getTime();
		List<Bill> lBills = billRepo.searchByDate(date);
		List<BillDTO> listBillDTOs = new ArrayList<>();
		for (Bill b : lBills) {
			listBillDTOs.add(new ModelMapper().map(b, BillDTO.class));
		}
		return listBillDTOs;
	}

	@Override
	public List<OrdersByBuyerName> countOrdersByBuyerName() {
		return billRepo.countOrdersByBuyerName();
	}

}
