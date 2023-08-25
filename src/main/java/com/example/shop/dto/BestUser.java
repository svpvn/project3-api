package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BestUser {
	private int idUser;
	private String nameUser;
	private double totalPricesSold;
}
