package com.coursenet.delivery.dto;

import com.coursenet.delivery.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusRequestDTO {
	private long id;
	private OrderStatus status;
}
