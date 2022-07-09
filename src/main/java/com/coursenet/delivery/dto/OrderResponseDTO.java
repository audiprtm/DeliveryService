package com.coursenet.delivery.dto;

import java.time.LocalDateTime;

import com.coursenet.delivery.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
	private long id;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String goodsName;	
	private String invoice;
	private OrderStatus status;
	private int shipperId;
}
