package com.prime.quickcart.order_service_api.util;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandardResponseDTO {
    private int status;
    private String message;
    private Object data;
}
