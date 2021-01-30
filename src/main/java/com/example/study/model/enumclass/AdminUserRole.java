package com.example.study.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminUserRole {

    ADMIN(0,"관리자"),
    TESTER(1, "테스트 계정")
    ;

    private Integer id;
    private String title;
}
