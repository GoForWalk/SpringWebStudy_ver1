package com.example.study.service;

import com.example.study.model.entity.OrderGroup;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.OrderGroupApiRequest;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderGroupApiLogicService extends BaseService<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {

    private static final String REGISTERED = "REGISTERED";
//
//    @Autowired
//    private OrderGroupRepository orderGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> request) {

        OrderGroupApiRequest body = request.getData();

        OrderGroup orderGroup = OrderGroup.builder()
                .status(REGISTERED)
                .orderType(body.getOrderType())
                .revAddress(body.getRevAddress())
                .revName(body.getRevName())
                .paymentType(body.getPaymentType())
                .totalPrice(body.getTotalPrice())
                .totalQuantity(body.getTotalQuantity())
                .orderAt(LocalDateTime.now())
                .user(userRepository.getOne(body.getUserId()))
                .arrivalDate(body.getArrivalDate())
                .build();

        OrderGroup newOrderGroup = baseRepository.save(orderGroup);

        return Header.Ok(response(newOrderGroup));
    }

    @Override
    public Header<OrderGroupApiResponse> read(Long id) {

        return baseRepository.findById(id)
                .map(orderGroup -> Header.Ok(response(orderGroup))).orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> request) {

        OrderGroupApiRequest body = request.getData();

        return baseRepository.findById(body.getId()).map(orderGroup -> {
            orderGroup.setStatus(body.getStatus())
                    .setOrderType(body.getOrderType())
                    .setRevName(body.getRevName())
                    .setRevAddress(body.getRevAddress())
                    .setPaymentType(body.getPaymentType())
                    .setTotalQuantity(body.getTotalQuantity())
                    .setTotalPrice(body.getTotalPrice())
                    .setOrderAt(body.getOrderAt())
                    .setArrivalDate(body.getArrivalDate())
                    .setUser(userRepository.getOne(body.getUserId()))
                    ;
            return orderGroup;
        }).map(orderGroup -> baseRepository.save(orderGroup))
                .map(newOrderGroup -> Header.Ok(response(newOrderGroup)))
                .orElseGet(()->Header.ERROR("NO DATA"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(orderGroup -> {baseRepository.delete(orderGroup); return Header.Ok();})
                .orElseGet(()->Header.ERROR("NO DATA"));
    }

    @Override
    public Header<List<OrderGroupApiResponse>> search(Pageable pageable) {

        Page<OrderGroup> orderGroups = baseRepository.findAll(pageable);

        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroups.stream()
                .map(orderGroup -> response(orderGroup))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(orderGroups.getTotalElements())
                .totalPage(orderGroups.getTotalPages())
                .currentPage(orderGroups.getNumber())
                .currentElements(orderGroups.getNumberOfElements())
                .build();

        return Header.Ok(orderGroupApiResponseList, pagination);
    }

    public OrderGroupApiResponse response(OrderGroup orderGroup){

        OrderGroupApiResponse body =  OrderGroupApiResponse.builder()
                .id(orderGroup.getId())
                .status(orderGroup.getStatus())
                .orderType(orderGroup.getOrderType())
                .revAddress(orderGroup.getRevAddress())
                .revName(orderGroup.getRevName())
                .paymentType(orderGroup.getPaymentType())
                .totalQuantity(orderGroup.getTotalQuantity())
                .totalPrice(orderGroup.getTotalPrice())
                .orderAt(orderGroup.getOrderAt())
                .arrivalDate(orderGroup.getArrivalDate())
                .userId(orderGroup.getUser().getId())
                .build()
                ;

        return body;
    }


}
