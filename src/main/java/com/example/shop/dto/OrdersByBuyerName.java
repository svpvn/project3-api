package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrdersByBuyerName {
	private long quantity;
	private String nameUser;
}
