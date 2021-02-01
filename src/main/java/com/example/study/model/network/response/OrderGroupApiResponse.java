package com.example.study.model.network.response;

import com.example.study.model.enumclass.OrderGroupOrderType;
import com.example.study.model.enumclass.OrderGroupPaymentType;
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
public class OrderGroupApiResponse {

    private Long id;

    private String status;
    private OrderGroupOrderType orderType;
    private String revAddress;
    private String revName;
    private String paymentType;
    private BigDecimal totalPrice;
    private int totalQuantity;
    private LocalDateTime orderAt;
    private LocalDateTime arrivalDate;


    private Long userId;
}
