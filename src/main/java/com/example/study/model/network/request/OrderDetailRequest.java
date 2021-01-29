package com.example.study.model.network.request;

import com.example.study.model.enumclass.OrderDetailStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDetailRequest {

    private Long id;
    private OrderDetailStatus status;
    private LocalDateTime arrivalDate;
    private Integer quantity;
    private BigDecimal totalPrice;

    private Long itemID;
    private Long orderGroupID;

}
