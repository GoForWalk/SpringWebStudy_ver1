package com.example.study.repository;

import com.example.study.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// JpaRepository를 상속
// <object type = entity class, primary key type>
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    // 쿼리메소드 작성

    // Account를 기준으로 하여 db를 검색 (where 구문)
    // select * from user where account = ?
//    Optional<User> findByAccount(String account); // Account 와 account를 매칭
//
//    // select * from user where email = ?
//    Optional<User> findByEmail(String email);
//
//    // select * from user where account = ? and email = ?
//    Optional<User> findByAccountAndEmail(String account, String email);

    User findFirstByPhoneNumberOrderByIdDesc(String phoneNumber);

//    User findById(Long id);

}
