package com.prime.quickcart.order_service_api.service;

import com.prime.quickcart.order_service_api.dto.request.CustomerOrderRequestDTO;
import com.prime.quickcart.order_service_api.dto.response.CustomerOrderResponseDTO;
import com.prime.quickcart.order_service_api.dto.response.paginate.CustomerOrderPaginateDTO;

public interface CustomerOrderService {
    public void createOrder(CustomerOrderRequestDTO requestDTO);
    public void updateOrder(CustomerOrderRequestDTO requestDTO, String orderId);
    public void manageRemark(String remark, String orderId);
    public void manageStatus(String status, String orderId);
    public CustomerOrderResponseDTO findOrderById(String orderId);
    public void deleteById(String orderId);
    public CustomerOrderPaginateDTO searchAll(String searchText, int page, int size);
}
