package com.example.study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user","item"}) // 연관관계 설정에 따른 변수는 서로 참조를 풀기 위해서 exclude 사용)
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderAt;

    // N : 1
    // 연관관계 설정을 할 때는 반드시 연관 관계인 객체이름을 적어야 한다.
    // User id 를 찾아간다.
    @ManyToOne
    private User user; // User id

    @ManyToOne
    private Item item; // Item id

}
