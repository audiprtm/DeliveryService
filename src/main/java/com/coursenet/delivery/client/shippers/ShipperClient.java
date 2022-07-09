package com.coursenet.delivery.client.shippers;

import com.coursenet.delivery.dto.ShipperRequestDTO;

public interface ShipperClient {
	public void requestPickup(ShipperRequestDTO shipperRequestDTO) throws Exception;
	public void cancelDelivery(ShipperRequestDTO shipperRequestDTO) throws Exception;
}
