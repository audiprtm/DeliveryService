package com.coursenet.delivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coursenet.delivery.entity.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
	Optional<Delivery> findByOrderId(long orderId);
	Optional<Delivery> findByInvoice(String invoice);
}
