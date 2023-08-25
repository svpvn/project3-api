package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BestsellingProduct {
	private long idProudct;
	private String nameProduct;
	private long totalProductsSold;
}
