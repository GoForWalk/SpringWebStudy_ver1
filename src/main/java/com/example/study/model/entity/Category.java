package com.example.study.model.entity;

import com.example.study.model.enumclass.CategoryType;
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
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "partnerList")
@EntityListeners(AuditingEntityListener.class)
@Builder // lombok 추가기능
@Accessors(chain = true) // lombok 추가기능
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // enum 사용
    @Enumerated(EnumType.STRING)
    private CategoryType type; // NOTEBOOK, DESKTOP, PHONE, TABLET, EARPHONE, MOUSE, KEYBOARD

    private String title;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy // @EntityListeners(AuditingEntityListener.class) 가 LoginUserAuditorAware에 설정된 값으로 작동시킨다.
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy // @EntityListeners(AuditingEntityListener.class) 가 LoginUserAuditorAware에 설정된 값으로 작동시킨다.
    private String updatedBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Partner> partnerList;

}
