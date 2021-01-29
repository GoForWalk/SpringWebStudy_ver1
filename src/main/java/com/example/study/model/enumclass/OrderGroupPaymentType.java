package com.example.study.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderGroupPaymentType {

    CARD(0, "카드", "카트 결제"),
    CASH(1,"현금", "현금 결제")
    ;

    private Integer id;
    private String title;
    private String content;

}
