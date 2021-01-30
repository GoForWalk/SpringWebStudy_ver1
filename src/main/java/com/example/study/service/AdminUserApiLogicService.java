package com.example.study.service;

import com.example.study.model.entity.AdminUser;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.AdminUserRequest;
import com.example.study.model.network.response.AdminUserResponse;
import org.springframework.stereotype.Service;

@Service
public class AdminUserApiLogicService extends BaseService<AdminUserRequest, AdminUserResponse, AdminUser>{

    @Override
    public Header<AdminUserResponse> create(Header<AdminUserRequest> request) {

        AdminUserRequest body = request.getData();

        AdminUser adminUser = AdminUser.builder()
                .account(body.getAccount())
                .password(body.getPassword())
                .status(body.getStatus())
                .role(body.getRole())
                .lastLoginAt(body.getLastLoginAt())
                .passwordUpdatedAt(body.getPasswordUpdatedAt())
                .loginFailCount(body.getLoginFailCount())
                .registeredAt(body.getRegisteredAt())
                .unregisteredAt(body.getUnregisteredAt())
                .build();

        return response(baseRepository.save(adminUser));
    }

    @Override
    public Header<AdminUserResponse> read(Long id) {
        return baseRepository.findById(id).map(this::response).orElseGet(() ->Header.ERROR("NO DATA"));
    }

    @Override
    public Header<AdminUserResponse> update(Header<AdminUserRequest> request) {

        AdminUserRequest body = request.getData();

        return baseRepository.findById(body.getId()).map(adminUser -> {

            adminUser.setAccount(adminUser.getAccount())
                    .setPassword(adminUser.getPassword())
                    .setStatus(adminUser.getStatus())
                    .setRole(adminUser.getRole())
                    .setLastLoginAt(adminUser.getLastLoginAt())
                    .setPasswordUpdatedAt(adminUser.getPasswordUpdatedAt())
                    .setLoginFailCount(adminUser.getLoginFailCount())
                    .setRegisteredAt(adminUser.getRegisteredAt())
                    .setUnregisteredAt(adminUser.getUnregisteredAt())
                    ;

            return adminUser;
        }).map(updateAdminUser -> baseRepository.save(updateAdminUser))
                .map(this::response)
                .orElseGet(()-> Header.ERROR("NO DATA"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id).map(adminUser -> {baseRepository.delete(adminUser); return Header.Ok();})
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    private Header<AdminUserResponse> response(AdminUser adminUser){

        AdminUserResponse adminUserResponse = AdminUserResponse.builder()
                .id(adminUser.getId())
                .account(adminUser.getAccount())
                .password(adminUser.getPassword())
                .status(adminUser.getStatus())
                .role(adminUser.getRole())
                .lastLoginAt(adminUser.getLastLoginAt())
                .passwordUpdatedAt(adminUser.getPasswordUpdatedAt())
                .loginFailCount(adminUser.getLoginFailCount())
                .registeredAt(adminUser.getRegisteredAt())
                .unregisteredAt(adminUser.getUnregisteredAt())
                .build();

        return Header.Ok(adminUserResponse);
    }
}
