package com.coursenet.delivery.dto;

import com.coursenet.delivery.enums.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatusRequestDTO {
	private long orderId;
	private String invoice;
	private int shipperId;
	private DeliveryStatus status;
}
