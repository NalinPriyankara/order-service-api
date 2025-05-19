package com.prime.quickcart.order_service_api.service;

import com.prime.quickcart.order_service_api.dto.request.CustomerOrderRequestDTO;

public interface CustomerOrderService {
    public void createOrder(CustomerOrderRequestDTO requestDTO);
}
