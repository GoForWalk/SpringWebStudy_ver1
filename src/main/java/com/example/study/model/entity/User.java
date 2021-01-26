package com.example.study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity // == table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String account;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    // 1 : N
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<OrderDetail> orderDetailList;

    // LAZY = 지연로딩 , EAGER = 즉시로딩

    // LAZY : get 메소드를 호출하지 않는 이상, 연관 관계가 설정된 테이블들에 대해서 SELECT 하지 않겠다. - JOIN 할 때 LAZY 추천
    // LAZY = SELECT FROM item WHERE id = ?

    // EAGER = 1:1 관계시 사용
    // EAGER : 즉시 모든것을 다 로딩하겠다. 연관 관계가 설정된 모든 테이블에 대해서 JOIN이 일어난다.
    //              단점: 데이터 로딩시 속도가 느려질 수 있다.
    // EAGER =
    // item_id = order_detail.item_id
    // user_id = order_detail.user_id
    // where item.id = ?
}
