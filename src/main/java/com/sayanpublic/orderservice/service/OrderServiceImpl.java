package com.sayanpublic.orderservice.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sayanpublic.orderservice.constants.OrderStatus;
import com.sayanpublic.orderservice.entity.Order;
import com.sayanpublic.orderservice.external.client.ProductService;
import com.sayanpublic.orderservice.model.OrderRequest;
import com.sayanpublic.orderservice.repository.OrderRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductService productService;
	
	@Override
	public long placeOrder(OrderRequest orderRequest) {
		// Order Entity -> save data with status Order Created
		// Product Service -> Block products (reduce quantity)
		// Payment Service -> Payment -> Success COMPLETE else CANCELLED
		log.info("Placing Order Request: {}", orderRequest);
		
		productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
		
		log.info("Creating order with status CREATED");
		Order order = Order.builder()
				.amount(orderRequest.getTotalAmount())
				.orderStatus(OrderStatus.CREATED)
				.productId(orderRequest.getProductId())
				.orderDate(Instant.now())
				.quantity(orderRequest.getQuantity()) 
				.build();
		order = orderRepository.save(order);
		log.info("Order placed successfully with Order Id: {}", order.getId());
		return order.getId();
	}

}
