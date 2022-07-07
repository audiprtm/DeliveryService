package com.coursenet.delivery.dto;

import java.time.LocalDateTime;

import com.coursenet.delivery.entity.Delivery;
import com.coursenet.delivery.enums.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponseDTO {
	public DeliveryResponseDTO(Delivery delivery) {
		super();
		this.id = delivery.getId();
		this.orderId = delivery.getOrderId();
		this.invoice = delivery.getInvoice();
		this.shipperId = delivery.getShipperId();
		this.createdAt = delivery.getCreatedAt();
		this.updatedAt = delivery.getUpdatedAt();
		this.status = delivery.getStatus();
	}

	private long id;
	private long orderId;
	private String invoice;
	private int shipperId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private DeliveryStatus status;
}
