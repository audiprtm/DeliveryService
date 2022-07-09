package com.coursenet.delivery.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class GojekRequestDTO {
	private String invoice;

	public GojekRequestDTO(String invoice) {
		super();
		this.invoice = invoice;
	}
}
