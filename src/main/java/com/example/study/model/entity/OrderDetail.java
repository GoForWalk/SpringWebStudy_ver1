package com.example.study.model.entity;

import com.example.study.model.enumclass.OrderDetailStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"item", "orderGroup"}) // 연관관계 설정에 따른 변수는 서로 참조를 풀기 위해서 exclude 사용)
@Builder // lombok 추가기능
@Accessors(chain = true) // lombok 추가기능
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Enumerated(EnumType.STRING)
    private String status;

    private LocalDateTime arrivalDate;
    private Integer quantity;
    private BigDecimal totalPrice;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy // @EntityListeners(AuditingEntityListener.class) 가 LoginUserAuditorAware에 설정된 값으로 작동시킨다.
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy // @EntityListeners(AuditingEntityListener.class) 가 LoginUserAuditorAware에 설정된 값으로 작동시킨다.
    private String updatedBy;

    @ManyToOne
    private Item item;

    @ManyToOne
    private OrderGroup orderGroup;


    // N : 1
    // 연관관계 설정을 할 때는 반드시 연관 관계인 객체이름을 적어야 한다.
    // User id 를 찾아간다.
//    @ManyToOne
//    private User user; // User id
//
//    @ManyToOne
//    private Item item; // Item id

}
