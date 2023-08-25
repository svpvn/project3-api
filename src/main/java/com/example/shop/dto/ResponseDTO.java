package com.example.shop.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder // Sử dụng cái này khi tao hàm chỉ cần chấm không cần new
@NoArgsConstructor // @NoArgsConstructor trong Java được sử dụng để tạo ra một constructor không có tham số,
@AllArgsConstructor //trong Java được sử dụng để tạo ra một constructor chứa tất cả các tham số của lớp
public class ResponseDTO<T> implements Serializable{
	private int status;
	private String message;
	// Khi xét dữ  không null, null sẽ không trả về sẽ có respon trả về
	@JsonInclude(Include.NON_NULL)
	private T data;
}
