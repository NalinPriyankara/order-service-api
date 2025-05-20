package com.prime.quickcart.order_service_api.service;

import com.prime.quickcart.order_service_api.dto.request.CustomerOrderRequestDTO;
import com.prime.quickcart.order_service_api.dto.response.CustomerOrderResponseDTO;

public interface CustomerOrderService {
    public void createOrder(CustomerOrderRequestDTO requestDTO);
    public CustomerOrderResponseDTO findOrderById(String orderId);
}
