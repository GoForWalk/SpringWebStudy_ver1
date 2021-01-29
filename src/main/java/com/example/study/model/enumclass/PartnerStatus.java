package com.example.study.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PartnerStatus {

    REGISTERED(0, "승인", "파트너사에 등록된 회사"),
    UNREGISTERED(1, "미승인", "파트너사에 미등록된 회사"),
    WAITING(2,"승인 대기중", "파트너사로 승인 심사 중")
    ;

    private Integer id;
    private String title;
    private String content;

}
