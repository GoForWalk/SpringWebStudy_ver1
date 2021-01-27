package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class ItemRepositoryTest extends StudyApplicationTests {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create(){

//        Item item = new Item();
//
////        item.setId();
//        item.setName("노트북");
////        item.setPrice(100000);
//        item.setContent("삼성 노트북");
//
//        Item newItem = itemRepository.save(item);
//        Assertions.assertNotNull(newItem);

        Item item = new Item();

        item.setName("노트북");
        item.setStatus("REGISTERED");
        item.setTitle("Labtop");
        item.setContent("Dell Laptop");
        item.setPrice(new BigDecimal(900000));
        item.setBrandName("Dell");
        item.setRegisteredAt(LocalDateTime.now());
        item.setCreatedAt(LocalDateTime.now());
        item.setCreatedBy("AdminServer");

//        item.setPartnerId(1L);

        Item newItem = itemRepository.save(item);

        Assertions.assertNotNull(newItem);
        Assertions.assertEquals(newItem.getBrandName(), "Dell");


    }

    @Test
    @Transactional
    public void read(){

        Long id = 1L;

        Optional<Item> item = itemRepository.findById(id);

        Assertions.assertTrue(item.isPresent());

        item.ifPresent(selectItem ->{
            System.out.println(selectItem);
//            selectItem.setName("고급 노트북");
//            selectItem.setPrice(200000);
//            selectItem.setContent("삼성 고급 노트북");
        });

    }
}

