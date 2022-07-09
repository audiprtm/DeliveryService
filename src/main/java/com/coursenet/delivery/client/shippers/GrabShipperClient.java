package com.coursenet.delivery.client.shippers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.coursenet.delivery.dto.GojekResponseDTO;
import com.coursenet.delivery.dto.GrabRequestDTO;
import com.coursenet.delivery.dto.ShipperRequestDTO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GrabShipperClient implements ShipperClient{
	private RestTemplate restTemplate = new RestTemplate();
	
	@Value("${grab.base.url}")
	private String grabServiceBaseURL;
	
	@Value("${grab.requestPickup.url}")
	private String grabRequestPickupURL;
	
	public void requestPickup(ShipperRequestDTO shipperRequestDTO) throws Exception {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    GrabRequestDTO grabRequestDTO = GrabRequestDTO
	    		.builder()
	    		.invoice(shipperRequestDTO.getInvoice())
	    		.build();
	    
	    
	    log.info("Request Pickup GrabShipperClient Started, "
				+",Request: "+grabRequestDTO.toString());
	    
		try {
			HttpEntity<GrabRequestDTO> requestEntity = new HttpEntity<>(grabRequestDTO, headers);

			restTemplate.postForObject(
					String.format("%s%s", grabServiceBaseURL, grabRequestPickupURL),
					requestEntity, 
					GojekResponseDTO.class
					);
		} catch (Exception e) {
			throw new Exception();
		}
		
	    log.info("Request Pickup GrabShipperClient Finished, "
				+", Request: "+grabRequestDTO.toString());
	}
	
	public void cancelDelivery(ShipperRequestDTO shipperRequestDTO) throws Exception {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    GrabRequestDTO grabRequestDTO = GrabRequestDTO
	    		.builder()
	    		.invoice(shipperRequestDTO.getInvoice())
	    		.build();
	    
	    log.info("Cancel Delivery GrabShipperClient Started, "
				+",Request: "+grabRequestDTO.toString());
	    
		try {
			HttpEntity<GrabRequestDTO> requestEntity = new HttpEntity<>(grabRequestDTO, headers);

			restTemplate.postForObject(
					String.format("%s%s", grabServiceBaseURL, grabRequestPickupURL),
					requestEntity, 
					GojekResponseDTO.class
					);
		} catch (Exception e) {
			throw new Exception();
		}
		
	    log.info("Cancel Delivery GrabShipperClient Finished, "
				+", Request: "+grabRequestDTO.toString());
	}
}
