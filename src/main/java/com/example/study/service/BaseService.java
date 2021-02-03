package com.example.study.service;

import com.example.study.controller.ifs.CrudInterface;
import com.example.study.model.network.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseService<Req, Res, Entity> implements CrudInterface<Req, Res> {

    @Autowired(required = false) // 반드시 default 는 아니다.
    protected JpaRepository<Entity, Long> baseRepository;
        // JpaRepository<Item, Long>

    // Service 부분에서는 Controller 랑은 다르게, CRUD 단을 모두 재정의해야한다.

    public Res response(Entity entity) {
        return null;
    }


}
