package com.example.study.model.entity;

import com.example.study.model.enumclass.UserStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity // == table
@EntityListeners(AuditingEntityListener.class) // JpaConfig(@EnableJpaAuditing) -> LoginUserAuditorAware
@ToString(exclude = "orderGroupList")
@Builder // lombok 추가기능
@Accessors(chain = true) // lombok 추가기능
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String account;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING) // JPA Enum 적용 -> Service 단에서 활용 가능.
    private UserStatus status; // REGISTERED / UNREGISTERED / WAITING -> Enum 의 형태로 관리

    private String phoneNumber;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy // @EntityListeners(AuditingEntityListener.class) 가 LoginUserAuditorAware에 설정된 값으로 작동시킨다.
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy // @EntityListeners(AuditingEntityListener.class) 가 LoginUserAuditorAware에 설정된 값으로 작동시킨다.
    private String updatedBy;

    // User : 1 , N : OrderGroup
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<OrderGroup> orderGroupList;



    // 1 : N
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    private List<OrderDetail> orderDetailList;

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
