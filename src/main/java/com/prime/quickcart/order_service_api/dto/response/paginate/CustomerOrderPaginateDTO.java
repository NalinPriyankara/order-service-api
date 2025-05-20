package com.prime.quickcart.order_service_api.dto.response.paginate;

import com.prime.quickcart.order_service_api.dto.response.CustomerOrderResponseDTO;
import com.prime.quickcart.order_service_api.entity.CustomerOrder;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrderPaginateDTO {
    private long count;
    private List<CustomerOrderResponseDTO> dataList;
}
