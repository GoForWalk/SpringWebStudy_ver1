package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class UserRepositoryTest extends StudyApplicationTests {

    // DI 의존적 주입 : 사용자가 직접 객체를 만들지 않고, 스프링에서 객체를 관리
    // DI 의 기본 : Singleton
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {
        // String sql = insert into user (%s, %s, %d) value ( account, email, age);

//        //jpa 의 도움!!
//        User user = new User();
////        user.setId(); // 자동으로 생성
//        user.setAccount("TestUser03"); //NOT NULL
//        user.setEmail("TestUser03@gmail.com");
//        user.setPhoneNumber("010-3333-3333");
//        user.setCreatedAt(LocalDateTime.now());
//        user.setCreatedBy("admin");
//
//        User newUser = userRepository.save(user);
//        System.out.println("newUser : " + newUser);

//        User user = new User();

        String account = "Test03";
        String password = "pass03";
        String status = "REGISTERED";
        String email = "Test03@naver.com";
        String phoneNumber = "010-0000-3333";
        LocalDateTime registeredAt = LocalDateTime.now();
//        LocalDateTime createdAt = LocalDateTime.now();
//        String createdBy = "AdminServer";

        // Builder pattern 사용
        User u = User.builder() // builder pattern start
                .account(account) // 생성자에 넣을 parameter
                .password(password)
                .status(UserStatus.valueOf(status))
                .email(email)
                .phoneNumber(phoneNumber)
                .build(); // builder pattern end

        // Chaining 사용
//        user.setAccount(account).setPassword(password).setPhoneNumber(phoneNumber);
//
//        // chaining 미사용
//        user.setAccount(account);
//        user.setPassword(password);
//        user.setPhoneNumber(phoneNumber);
//        user.setStatus(UserStatus.valueOf(status));
//        user.setEmail(email);
////        user.setCreatedAt(createdAt);
////        user.setCreatedBy(createdBy);
//        user.setRegisteredAt(registeredAt);

        log.info("u : {}", u);
        User newUser = userRepository.save(u);

        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(newUser.getAccount(), account);
        Assertions.assertEquals(newUser.getPassword(), password);

    }


    @Test
    @Transactional
    public void read() {

        // select * from user where id = ?
//        Optional<User> user = userRepository.findById(4L);
//        // 2L : 2 + key 의 타입
//        // Optional ?? : 있을수도 있고, 없을 수도 있다.
//
//        // ifPresent() : Optional 타입의 함수 : 존재할 경우!
//        user.ifPresent(selectUser -> {
//
//            selectUser.getOrderDetailList().stream().forEach(detail -> {
//                Item item = detail.getItem();
//                System.out.println(item);
//            });
//
//        });
//

        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-0000-0000");

        user.getOrderGroupList().stream().forEach(orderGroup -> {

            System.out.println("-------------------주문 묶음-------------------");
            System.out.println("수령인 : " + orderGroup.getRevName());
            System.out.println("수령지 : " + orderGroup.getRevAddress());
            System.out.println("총 가격 : " + orderGroup.getTotalPrice());
            System.out.println("총 수량 " + orderGroup.getTotalQuantity());

            System.out.println("-------------------주문 상세-------------------");

            orderGroup.getOrderDetailList().forEach(orderDetail -> {

                System.out.println("파트너사 이름 : " + orderDetail.getItem().getPartner().getName());
                System.out.println("파트너사 카테고리 :" + orderDetail.getItem().getPartner().getCategory().getTitle());
                System.out.println("주문 상품 : " + orderDetail.getItem().getName());
                System.out.println("고객 센터 번호 : " + orderDetail.getItem().getPartner().getCallCenter());
                System.out.println("주문의 상태 : " + orderDetail.getStatus());
                System.out.println("도착예정일자 : " + orderDetail.getArrivalDate());

            });

        });

        Assertions.assertNotNull(user);

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
