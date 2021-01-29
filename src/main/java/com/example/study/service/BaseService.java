package com.example.study.service;

import com.example.study.controller.ifs.CrudInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseService<Res, Req, Entity> implements CrudInterface<Res, Req> {

    @Autowired(required = false) // 반드시 default 는 아니다.
    protected JpaRepository<Entity, Long> baseRepository;
        // JpaRepository<Item, Long>

    // Service 부분에서는 Controller 랑은 다르게, CRUD 단을 모두 재정의해야한다.


}
