package com.coursenet.delivery.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.coursenet.delivery.client.OrderServiceClient;
import com.coursenet.delivery.client.shippers.GojekShipperClient;
import com.coursenet.delivery.client.shippers.GrabShipperClient;
import com.coursenet.delivery.client.shippers.ShipperClient;
import com.coursenet.delivery.constants.ShipperConstants;
import com.coursenet.delivery.dto.DeliveryRequestDTO;
import com.coursenet.delivery.dto.DeliveryResponseDTO;
import com.coursenet.delivery.dto.DeliveryStatusRequestDTO;
import com.coursenet.delivery.dto.OrderStatusRequestDTO;
import com.coursenet.delivery.dto.ShipperRequestDTO;
import com.coursenet.delivery.entity.Delivery;
import com.coursenet.delivery.enums.DeliveryStatus;
import com.coursenet.delivery.enums.OrderStatus;
import com.coursenet.delivery.repository.DeliveryRepository;
import com.coursenet.delivery.utl.ShipperMapperUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeliveryService {
	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Autowired
	private OrderServiceClient orderServiceClient;
	
	@Autowired
	private ShipperMapperUtil shipperMapperUtil;

	public ResponseEntity<DeliveryResponseDTO> createDelivery(DeliveryRequestDTO deliveryRequest) {		
		Delivery delivery = deliveryRepository.save(
				Delivery.builder()
				.orderId(deliveryRequest.getOrderId())
				.invoice(deliveryRequest.getInvoice())
				.shipperId(deliveryRequest.getShipperId())
				.goodsName(deliveryRequest.getGoodsName())
				.status(DeliveryStatus.CREATED)
				.build()
				);
		
		DeliveryResponseDTO deliveryResponseDTO = new DeliveryResponseDTO(delivery);
		
		log.info("Create Delivery Controller Finished, "
				+ "Response: "+ deliveryResponseDTO.toString()
				+",Request: "+deliveryRequest.toString());
		return new ResponseEntity<>(deliveryResponseDTO, HttpStatus.CREATED);
	}

	public ResponseEntity<DeliveryResponseDTO> updateDeliveryStatus(String token,
			DeliveryStatusRequestDTO deliveryStatusRequest) {
		
		Optional<Delivery> delivery = deliveryRepository.findByOrderId(deliveryStatusRequest.getOrderId());
		if(!delivery.isPresent()) {
			delivery = deliveryRepository.findByInvoice(deliveryStatusRequest.getInvoice());
			if(!delivery.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		
		DeliveryResponseDTO deliveryResponseDTO;	
		switch (deliveryStatusRequest.getStatus()) {
			case IN_DELIVERY:
				deliveryResponseDTO = inDelivery(delivery.get());
				break;
			case DELIVERED:
				deliveryResponseDTO = delivered(token, delivery.get());
				break;
			case CANCELLED:
				deliveryResponseDTO = cancelDelivery(token, delivery.get());
				break;
			default:
				deliveryResponseDTO = null;
				break;
		}
		
		if(deliveryResponseDTO==null) {
			log.error("Update delivery status failed"+ " request: "+deliveryStatusRequest.toString());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		log.info("Update delivery Controller Finished, "
				+ "request: "+deliveryStatusRequest.toString()
				+ ", response: "+deliveryResponseDTO.toString());
		return new ResponseEntity<>(deliveryResponseDTO,HttpStatus.OK);
	}
	
	private DeliveryResponseDTO cancelDelivery(String token,Delivery delivery) {
		if(delivery.getStatus()==DeliveryStatus.IN_DELIVERY) {
			return null;
		}
		
		ShipperClient shipperClient = shipperMapperUtil.mapShipperClient(delivery.getShipperId());
		if(shipperClient==null) {
			return null;
		}
		
		try {
			shipperClient.cancelDelivery(
					ShipperRequestDTO
					.builder()
					.invoice(delivery.getInvoice())
					.build()
					);
		} catch (Exception e) {
			log.error(e.toString());
			return null;
		}
		
		delivery.setStatus(DeliveryStatus.CANCELLED);
		delivery = deliveryRepository.save(delivery);
		return new DeliveryResponseDTO(delivery);
	}

	private DeliveryResponseDTO inDelivery(Delivery delivery) {
		if(delivery.getStatus()!=DeliveryStatus.CREATED) {
			return null;
		}	
		
		ShipperClient shipperClient = shipperMapperUtil.mapShipperClient(delivery.getShipperId());
		if(shipperClient==null) {
			return null;
		}
		
		try {
			shipperClient.requestPickup(ShipperRequestDTO
					.builder()
					.invoice(delivery.getInvoice())
					.build()
					);
		} catch (Exception e) {
			log.error(e.toString());
			return null;
		}
		
		delivery.setStatus(DeliveryStatus.IN_DELIVERY);
		delivery = deliveryRepository.save(delivery);
		return new DeliveryResponseDTO(delivery);
	}

	private DeliveryResponseDTO delivered(String token, Delivery delivery) {
		if(delivery.getStatus()!=DeliveryStatus.IN_DELIVERY) {
			return null;
		}
		
		try {
			orderServiceClient.updateStatus(
					token, 
					OrderStatusRequestDTO.builder()
					.id(delivery.getOrderId())
					.status(OrderStatus.FINISHED)
					.build()
					);
		} catch (Exception e) {
			log.error(e.toString());
			return null;
		}
		
		delivery.setStatus(DeliveryStatus.DELIVERED);
		delivery = deliveryRepository.save(delivery);
		return new DeliveryResponseDTO(delivery);
	}
}
