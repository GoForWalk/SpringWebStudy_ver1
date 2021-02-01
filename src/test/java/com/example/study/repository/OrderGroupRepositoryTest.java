package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.enumclass.OrderGroupOrderType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderGroupRepositoryTest extends StudyApplicationTests {

    @Autowired
    OrderGroupRepository orderGroupRepository;

    @Test
    public void create(){

        OrderGroup orderGroup = new OrderGroup();

        orderGroup.setStatus("Waiting");
        orderGroup.setOrderType(OrderGroupOrderType.ALL);
        orderGroup.setRevAddress("서울시 광진구");
        orderGroup.setRevName("홍길동");
        orderGroup.setPaymentType("Cash");
        orderGroup.setTotalPrice(BigDecimal.valueOf(900000));
        orderGroup.setTotalQuantity(1);
        orderGroup.setCreatedBy("AdminServer");
        orderGroup.setCreatedAt(LocalDateTime.now());

//        orderGroup.setUserId(1L);

        OrderGroup newOrderGroup = orderGroupRepository.save(orderGroup);

        Assertions.assertNotNull(newOrderGroup);
    }

    public void read(){

    }


}
