package com.example.study.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderInfoApiResponse {

    private UserApiResponse userApiResponse;

    // OrderGroupApiResponse / UserApiResponse 수정
    // 사용자의 모든 주문내역을 조회하는 것이 아니라,
    // 원하는 주문 그룹에서 가져오기를 해야한다.

    // userApiResponse -> OrderGroupApiResponse -> ItemApiResponse
}