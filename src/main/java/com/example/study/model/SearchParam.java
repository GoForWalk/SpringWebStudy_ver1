package com.example.study.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // 모든 매게변수를 갖는 생성자
public class SearchParam {

    private String account;
    private String email;
    private int page;

    // {"account" : "" , "email" : "" , "page" : 0}
}
