package com.example.shop.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@EntityListeners(AuditingEntityListener.class)
@Data
@MappedSuperclass //được sử dụng để đánh dấu một lớp cha abstract trong JPA.
public abstract class TimeAuditable {
	
	@CreatedDate // auto gen new date
	@Column(updatable = false) // k cap nhat
	private Date createAt; //java.util
	
	@LastModifiedDate // auto update ngay update
	private Date updateAt;
}
