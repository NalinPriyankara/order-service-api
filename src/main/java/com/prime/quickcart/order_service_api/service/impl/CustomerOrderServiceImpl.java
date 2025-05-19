package com.prime.quickcart.order_service_api.service.impl;

import com.prime.quickcart.order_service_api.dto.request.CustomerOrderRequestDTO;
import com.prime.quickcart.order_service_api.dto.request.OrderDetailRequestDTO;
import com.prime.quickcart.order_service_api.entity.CustomerOrder;
import com.prime.quickcart.order_service_api.entity.OrderDetail;
import com.prime.quickcart.order_service_api.entity.OrderStatus;
import com.prime.quickcart.order_service_api.repo.CustomerOrderRepo;
import com.prime.quickcart.order_service_api.repo.OrderStatusRepo;
import com.prime.quickcart.order_service_api.service.CustomerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepo customerOrderRepo;
    private final OrderStatusRepo orderStatusRepo;

    @Override
    public void createOrder(CustomerOrderRequestDTO requestDTO) {
        OrderStatus orderStatus = orderStatusRepo.findByStatus("PENDING").orElseThrow(() -> new RuntimeException("Order status not found. So you cannot create a new order. Please contact admin"));

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderId(UUID.randomUUID().toString());
        customerOrder.setOrderDate(requestDTO.getOrderDate());
        customerOrder.setRemark("");
        customerOrder.setTotalAmount(requestDTO.getTotalAmount());
        customerOrder.setUserId(requestDTO.getUserId());
        customerOrder.setOrderStatus(orderStatus);
        customerOrder.setProducts(
                requestDTO.getOrderDetails().stream().map(e -> createOrderDetail(e, customerOrder)).collect(Collectors.toSet())
        );
        customerOrderRepo.save(customerOrder);
    }
    private OrderDetail createOrderDetail(OrderDetailRequestDTO requestDTO, CustomerOrder order) {
        if(requestDTO == null){
            return null;
        }
        return OrderDetail.builder()
                .detailId(UUID.randomUUID().toString())
                .unitPrice(requestDTO.getUnitPrice())
                .discount(requestDTO.getDiscount())
                .qty(requestDTO.getQty())
                .customerOrder(order)
                .build();
    }
}
