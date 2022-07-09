package com.coursenet.delivery.client.shippers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.coursenet.delivery.dto.GojekRequestDTO;
import com.coursenet.delivery.dto.GojekResponseDTO;
import com.coursenet.delivery.dto.ShipperRequestDTO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GojekShipperClient implements ShipperClient{
	private RestTemplate restTemplate = new RestTemplate();
	
	@Value("${gojek.base.url}")
	private String gojekServiceBaseURL;
	
	@Value("${gojek.requestPickup.url}")
	private String gojekRequestPickupURL;
	
	public void requestPickup(ShipperRequestDTO shipperRequestDTO) throws Exception {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    GojekRequestDTO gojekRequestDTO = GojekRequestDTO
	    		.builder()
	    		.invoice(shipperRequestDTO.getInvoice())
	    		.build();
	    
	    
	    log.info("Request Pickup GojekShipperClient Started, "
				+",Request: "+gojekRequestDTO.toString());
	    
		try {
			HttpEntity<GojekRequestDTO> requestEntity = new HttpEntity<>(gojekRequestDTO, headers);

			restTemplate.postForObject(
					String.format("%s%s", gojekServiceBaseURL, gojekRequestPickupURL),
					requestEntity, 
					GojekResponseDTO.class
					);
		} catch (Exception e) {
			throw new Exception();
		}
		
	    log.info("Request Pickup GojekShipperClient Finished, "
				+", Request: "+gojekRequestDTO.toString());
	}
	
	public void cancelDelivery(ShipperRequestDTO shipperRequestDTO) throws Exception {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    GojekRequestDTO gojekRequestDTO = GojekRequestDTO
	    		.builder()
	    		.invoice(shipperRequestDTO.getInvoice())
	    		.build();
	    
	    log.info("Cancel Delivery GojekShipperClient Started, "
				+",Request: "+gojekRequestDTO.toString());
	    
		try {
			HttpEntity<GojekRequestDTO> requestEntity = new HttpEntity<>(gojekRequestDTO, headers);

			restTemplate.postForObject(
					String.format("%s%s", gojekServiceBaseURL, gojekRequestPickupURL),
					requestEntity, 
					GojekResponseDTO.class
					);
		} catch (Exception e) {
			throw new Exception();
		}
		
	    log.info("Cancel Delivery GojekShipperClient Finished, "
				+", Request: "+gojekRequestDTO.toString());
	}
}
