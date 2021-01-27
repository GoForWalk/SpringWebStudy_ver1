package com.example.study.model.entity;

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
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"user", "orderDetailList"})
@Builder // lombok 추가기능
@Accessors(chain = true) // lombok 추가기능
public class OrderGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private String  orderType; // 주문의 형태 - 일괄 / 개별
    private String revAddress;
    private String revName;
    private String paymentType; // 카드 / 현금
    private BigDecimal totalPrice;
    private Integer totalQuantity;
    private LocalDateTime orderAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy // @EntityListeners(AuditingEntityListener.class) 가 LoginUserAuditorAware에 설정된 값으로 작동시킨다.
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy // @EntityListeners(AuditingEntityListener.class) 가 LoginUserAuditorAware에 설정된 값으로 작동시킨다.
    private String updatedBy;

    @ManyToOne
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderGroup")
    private List<OrderDetail> orderDetailList;

}
