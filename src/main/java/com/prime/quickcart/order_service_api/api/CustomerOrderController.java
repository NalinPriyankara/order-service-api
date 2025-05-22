package com.prime.quickcart.order_service_api.api;

import com.prime.quickcart.order_service_api.dto.request.CustomerOrderRequestDTO;
import com.prime.quickcart.order_service_api.service.CustomerOrderService;
import com.prime.quickcart.order_service_api.util.StandardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer_orders")
@RequiredArgsConstructor
public class CustomerOrderController {
    private final CustomerOrderService customerOrderService;

    @PostMapping("/business")
    public ResponseEntity<StandardResponseDTO> create(@RequestBody CustomerOrderRequestDTO request) {
        customerOrderService.createOrder(request);
        return new ResponseEntity<>(new StandardResponseDTO(201,"Customer order has been created", null), HttpStatus.CREATED);
    }

    @GetMapping("/visitors/find-by-id/{id}")
    public ResponseEntity<StandardResponseDTO> findById(@PathVariable String id) {
        return new ResponseEntity<>(new StandardResponseDTO(200,"Customer order detail", customerOrderService.findOrderById(id)), HttpStatus.OK);
    }

    @PutMapping("/business/update-order/{id}")
    public ResponseEntity<StandardResponseDTO> updateOrder(@RequestBody CustomerOrderRequestDTO request, @PathVariable String id) {
        customerOrderService.updateOrder(request, id);
        return new ResponseEntity<>(new StandardResponseDTO(200,"Customer order has been updated", null), HttpStatus.OK);
    }

    @PutMapping("/business/update-remark/{id}")
    public ResponseEntity<StandardResponseDTO> manageRemark(@RequestParam String remark, @PathVariable String id) {
        customerOrderService.manageRemark(remark, id);
        return new ResponseEntity<>(new StandardResponseDTO(200,"Customer order has been updated", null), HttpStatus.OK);
    }

    @PutMapping("/business/update-status/{id}")
    public ResponseEntity<StandardResponseDTO> manageStatus(@RequestParam String status, @PathVariable String id) {
        customerOrderService.manageStatus(status, id);
        return new ResponseEntity<>(new StandardResponseDTO(200,"Customer order has been updated", null), HttpStatus.OK);
    }

    @DeleteMapping("/business/delete-by-id/{id}")
    public ResponseEntity<StandardResponseDTO> deleteById(@PathVariable String id) {
        customerOrderService.deleteById(id);
        return new ResponseEntity<>(new StandardResponseDTO(204,"Customer order has been deleted", null), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/visitors/search-all")
    public ResponseEntity<StandardResponseDTO> searchAll(@RequestParam String searchText, @RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(new StandardResponseDTO(200,"Customer order list", customerOrderService.searchAll(searchText, page, size)), HttpStatus.OK);
    }
}
