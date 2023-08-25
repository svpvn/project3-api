package com.example.shop.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageDTO<T> {
	private long totalElements;
	private int totalPages;
	private List<T> data;
}
