package com.example.shop.dto;

import lombok.Data;

@Data
public class SearchDTO {
	private String keyword;
	private Integer size;
	private Integer currentPages;
	private String sortedField;
}
