package com.coursenet.delivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.coursenet.delivery.dto.DeliveryRequestDTO;
import com.coursenet.delivery.dto.DeliveryResponseDTO;
import com.coursenet.delivery.dto.DeliveryStatusRequestDTO;
import com.coursenet.delivery.service.DeliveryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DeliveryController {
	@Autowired
	private DeliveryService deliveryService;
	
	@PostMapping("/deliveries")
	public ResponseEntity<DeliveryResponseDTO>createDelivery(@RequestBody DeliveryRequestDTO deliveryRequest) {
		log.info("Create delivery Controller Started, request: "+deliveryRequest.toString());
		return deliveryService.createDelivery(deliveryRequest);
	}
	
	@PostMapping("/deliveries/update-status")
	public ResponseEntity<DeliveryResponseDTO> updateDeliveryStatus(
			@RequestHeader("Authorization") String token,
			@RequestBody DeliveryStatusRequestDTO deliveryStatusRequest) {
		log.info("Update delivery Controller Started, request: "+deliveryStatusRequest.toString());
		return deliveryService.updateDeliveryStatus(token, deliveryStatusRequest);
	}
	
}
