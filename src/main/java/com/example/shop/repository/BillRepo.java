package com.example.shop.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.shop.dto.BestUser;
import com.example.shop.dto.BestsellingProduct;
import com.example.shop.dto.BillStatisticDTO2;
import com.example.shop.dto.OrdersByBuyerName;
import com.example.shop.entity.Bill;

public interface BillRepo extends JpaRepository<Bill, Integer>{
	@Query("SELECT COUNT(b.id), MONTH(b.createAt) , YEAR(b.createAt) FROM Bill b GROUP BY  MONTH(b.createAt) , YEAR(b.createAt)")
	List<Object[]> thongKeBill();
	//thong ke thang/nam
	@Query("SELECT new com.example.shop.dto.BillStatisticDTO2( COUNT(b.id), MONTH(b.createAt) , YEAR(b.createAt)) FROM Bill b GROUP BY  MONTH(b.createAt) , YEAR(b.createAt)")
	List<BillStatisticDTO2> thongKeBill2();
	//thong ke sl bill cua nguoi mua
	@Query("SELECT NEW com.example.shop.dto.OrdersByBuyerName( COUNT(b.id),u.name) FROM Bill b join b.user u GROUP BY b.user.id")
	List<OrdersByBuyerName> countOrdersByBuyerName();
	
	// san pham ban chay 
	@Query("SELECT NEW com.example.shop.dto.BestsellingProduct(p.id, p.name, SUM(bi.quantity)) "
			+ "FROM BillItem bi "
			+ "JOIN bi.product p "
			+ "GROUP BY p.id, p.name "
			+ "ORDER BY SUM(bi.quantity) DESC")
	List<BestsellingProduct> getBestsellingProducts();
	
	@Query("SELECT  new com.example.shop.dto.BestUser(b.user.id, b.user.name, SUM(bi.price)) "
			+ "FROM Bill b JOIN b.billItems bi GROUP BY b.user.id, b.user.name")
	List<BestUser> bestUser();
	
	@Query("SELECT b FROM Bill b WHERE b.createAt >= :x")
	List<Bill> searchByDate(@Param("x") Date d);
}
