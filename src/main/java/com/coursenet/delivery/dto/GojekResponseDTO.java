package com.coursenet.delivery.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class GojekResponseDTO {
	private String invoice;
	
	public GojekResponseDTO(String invoice) {
		super();
		this.invoice = invoice;
	}
}
