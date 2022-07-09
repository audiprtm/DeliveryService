package com.coursenet.delivery.utl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coursenet.delivery.client.shippers.GojekShipperClient;
import com.coursenet.delivery.client.shippers.GrabShipperClient;
import com.coursenet.delivery.client.shippers.ShipperClient;
import com.coursenet.delivery.constants.ShipperConstants;

@Component
public class ShipperMapperUtil {
	@Autowired
	private GojekShipperClient gojekShipperClient;
	
	@Autowired
	private GrabShipperClient grabShipperClient;
	
	public ShipperClient mapShipperClient(int shipperId) {
		if(shipperId== ShipperConstants.GOJEK) {
			return gojekShipperClient;
		}else if(shipperId== ShipperConstants.GRAB) {
			return grabShipperClient;
		}
		
		return null;
	}
	
}
