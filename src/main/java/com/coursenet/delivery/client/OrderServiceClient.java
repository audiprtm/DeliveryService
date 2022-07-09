package com.coursenet.delivery.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.coursenet.delivery.dto.OrderStatusRequestDTO;

import lombok.extern.slf4j.Slf4j;

import com.coursenet.delivery.dto.OrderResponseDTO;

@Component
@Slf4j
public class OrderServiceClient {
	private RestTemplate restTemplate = new RestTemplate();
	
	@Value("${order.base.url}")
	private String orderServiceBaseURL;
	
	@Value("${order.updateStatus.url.endpoint}")
	private String orderUpdateStatusURL;
	
	public void updateStatus(String token, OrderStatusRequestDTO orderStatusRequestDTO) throws Exception {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("Authorization", token);
	    
	    log.info("Update Status OrderServiceClient Started, "
				+",Request: "+orderStatusRequestDTO.toString());
	    
		try {
			HttpEntity<OrderStatusRequestDTO> requestEntity = new HttpEntity<>(orderStatusRequestDTO, headers);

			restTemplate.postForObject(
					String.format("%s%s", orderServiceBaseURL, orderUpdateStatusURL),
					requestEntity, 
					OrderResponseDTO.class
					);
		} catch (Exception e) {
			throw new Exception();
		}
		
	    log.info("Update Status OrderServiceClient Finished, "
				+",Request: "+orderStatusRequestDTO.toString());
	}
}
