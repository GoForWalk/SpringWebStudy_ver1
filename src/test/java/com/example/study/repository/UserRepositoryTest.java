package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {

    // DI 의존적 주입 : 사용자가 직접 객체를 만들지 않고, 스프링에서 객체를 관리
    // DI 의 기본 : Singleton
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {
        // String sql = insert into user (%s, %s, %d) value ( account, email, age);

        //jpa 의 도움!!
        User user = new User();
//        user.setId(); // 자동으로 생성
        user.setAccount("TestUser03"); //NOT NULL
        user.setEmail("TestUser03@gmail.com");
        user.setPhoneNumber("010-3333-3333");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("admin");

        User newUser = userRepository.save(user);
        System.out.println("newUser : " + newUser);
    }


    @Test
    @Transactional
    public void read() {

        // select * from user where id = ?
        Optional<User> user = userRepository.findById(4L);
        // 2L : 2 + key 의 타입
        // Optional ?? : 있을수도 있고, 없을 수도 있다.

        // ifPresent() : Optional 타입의 함수 : 존재할 경우!
        user.ifPresent(selectUser -> {

            selectUser.getOrderDetailList().stream().forEach(detail -> {
                Item item = detail.getItem();
                System.out.println(item);
            });

        });

    }

    @Test
    public void update() {
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(selectUser -> { // id는 바꿀수 없다!!
            selectUser.setAccount("pppp");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("update method()");

            userRepository.save(selectUser);
        });
    }

    @Test
    @Transactional // 해당 메소스들은 모두 실행되지만, 결과는 반영되지 않는다. (마지막에 rollback transaction)
//    @DeleteMapping("/api/user")
    public void delete(
//            @RequestParam Long id
    ) {
        Optional<User> user = userRepository.findById(3L);

        // 값이 있는지 확인
        // 있으면 delete
        Assertions.assertTrue(user.isPresent()); // true 아니면 에러

        user.ifPresent(selectUser -> {
            userRepository.delete(selectUser);
            System.out.println("selectUser 제거");
        });

//        Optional<User> deleteUser = userRepository.findById(3L);
//
//        Assertions.assertFalse(user.isPresent()); // false

    }
}
