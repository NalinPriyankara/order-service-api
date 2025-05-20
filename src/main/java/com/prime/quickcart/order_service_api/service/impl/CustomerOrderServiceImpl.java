package com.prime.quickcart.order_service_api.service.impl;

import com.prime.quickcart.order_service_api.dto.request.CustomerOrderRequestDTO;
import com.prime.quickcart.order_service_api.dto.request.OrderDetailRequestDTO;
import com.prime.quickcart.order_service_api.dto.response.CustomerOrderResponseDTO;
import com.prime.quickcart.order_service_api.dto.response.OrderDetailResponseDTO;
import com.prime.quickcart.order_service_api.dto.response.paginate.CustomerOrderPaginateDTO;
import com.prime.quickcart.order_service_api.entity.CustomerOrder;
import com.prime.quickcart.order_service_api.entity.OrderDetail;
import com.prime.quickcart.order_service_api.entity.OrderStatus;
import com.prime.quickcart.order_service_api.repo.CustomerOrderRepo;
import com.prime.quickcart.order_service_api.repo.OrderStatusRepo;
import com.prime.quickcart.order_service_api.service.CustomerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public void updateOrder(CustomerOrderRequestDTO requestDTO, String orderId) {
        CustomerOrder customerOrder = customerOrderRepo.findById(orderId).orElseThrow(() -> new RuntimeException(String.format("Order not found with %s", orderId)));
        customerOrder.setOrderDate(requestDTO.getOrderDate());
        customerOrder.setTotalAmount(requestDTO.getTotalAmount());
        customerOrderRepo.save(customerOrder);
    }

    @Override
    public CustomerOrderResponseDTO findOrderById(String orderId) {
        CustomerOrder customerOrder = customerOrderRepo.findById(orderId).orElseThrow(() -> new RuntimeException(String.format("Order not found with %s", orderId)));
        return toCustomerOrderResponseDTO(customerOrder);
    }

    @Override
    public void deleteById(String orderId) {
        CustomerOrder customerOrder = customerOrderRepo.findById(orderId).orElseThrow(() -> new RuntimeException(String.format("Order not found with %s", orderId)));
        customerOrderRepo.delete(customerOrder);
    }

    @Override
    public CustomerOrderPaginateDTO searchAll(String searchText, int page, int size) {
        return CustomerOrderPaginateDTO.builder()
                .count(
                        customerOrderRepo.searchCount(searchText)
                )
                .dataList(
                        customerOrderRepo.searchAll(searchText, PageRequest.of(page,size))
                                .stream().map(this::toCustomerOrderResponseDTO).collect(Collectors.toList())
                )
                .build();
    }

    private CustomerOrderResponseDTO toCustomerOrderResponseDTO(CustomerOrder customerOrder) {
        if(customerOrder == null) {
            return null;
        }
        return CustomerOrderResponseDTO.builder()
                .orderId(customerOrder.getOrderId())
                .orderDate(customerOrder.getOrderDate())
                .userId(customerOrder.getUserId())
                .totalAmount(customerOrder.getTotalAmount())
                .orderDetails(
                        customerOrder.getProducts().stream().map(this::toOrderDetailResponseDTO).collect(Collectors.toList())

                )
                .remark(customerOrder.getRemark())
                .status(customerOrder.getOrderStatus().getStatus())
                .build();
    }

    private OrderDetailResponseDTO toOrderDetailResponseDTO(OrderDetail orderDetail) {
        if(orderDetail == null) {
            return null;
        }
        return OrderDetailResponseDTO.builder()
                .productId(orderDetail.getProductId())
                .detailId(orderDetail.getDetailId())
                .discount(orderDetail.getDiscount())
                .qty(orderDetail.getQty())
                .unitPrice(orderDetail.getUnitPrice())
                .build();
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
