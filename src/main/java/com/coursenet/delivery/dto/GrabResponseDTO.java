package com.coursenet.delivery.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class GrabResponseDTO {
	private String invoice;
	
	public GrabResponseDTO(String invoice) {
		super();
		this.invoice = invoice;
	}
}
