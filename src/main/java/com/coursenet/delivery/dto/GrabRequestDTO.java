package com.coursenet.delivery.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class GrabRequestDTO {
	public String invoice;
	
	public GrabRequestDTO(String invoice){
		super();
		this.invoice = invoice;
	}
}
