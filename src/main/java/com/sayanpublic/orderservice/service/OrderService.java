package com.sayanpublic.orderservice.service;

import com.sayanpublic.orderservice.model.OrderRequest;

public interface OrderService {

	long placeOrder(OrderRequest orderRequest);

}
