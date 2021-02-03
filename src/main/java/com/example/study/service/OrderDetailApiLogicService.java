package com.example.study.service;

import com.example.study.model.entity.OrderDetail;
import com.example.study.model.enumclass.OrderDetailStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderDetailRequest;
import com.example.study.model.network.response.OrderDetailResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.OrderGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailApiLogicService extends BaseService<OrderDetailRequest, OrderDetailResponse, OrderDetail>{

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Override
    public Header<OrderDetailResponse> create(Header<OrderDetailRequest> request) {

        OrderDetailRequest body = request.getData();

        OrderDetail orderDetail = OrderDetail.builder()
                .status("REGISTERED")
                .arrivalDate(body.getArrivalDate())
                .quantity(body.getQuantity())
                .totalPrice(body.getTotalPrice())
                .item(itemRepository.getOne(body.getItemID()))
                .orderGroup(orderGroupRepository.getOne(body.getOrderGroupID()))
                .build();

        return Header.Ok(response(baseRepository.save(orderDetail)));
    }

    @Override
    public Header<OrderDetailResponse> read(Long id) {
        return baseRepository.findById(id).map(orderDetail -> Header.Ok(response(orderDetail))).orElseGet(()->Header.ERROR("NO DATA"));
    }

    @Override
    public Header<OrderDetailResponse> update(Header<OrderDetailRequest> request) {

        OrderDetailRequest body = request.getData();

        return baseRepository.findById(body.getId()).map(orderDetail -> {

            orderDetail.setStatus(body.getStatus())
                    .setArrivalDate(body.getArrivalDate())
                    .setQuantity(body.getQuantity())
                    .setTotalPrice(body.getTotalPrice())
                    .setItem(itemRepository.getOne(body.getItemID()))
                    .setOrderGroup(orderGroupRepository.getOne(body.getOrderGroupID()))
                    ;
            return orderDetail;

        }).map(updateOrderDetail -> baseRepository.save(updateOrderDetail))
                .map(newOrderDetail -> Header.Ok(response(newOrderDetail)))
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id).map(orderDetail -> {baseRepository.delete(orderDetail); return Header.Ok();}).orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header<List<OrderDetailResponse>> search(Pageable pageable) {

        Page<OrderDetail> orderDetails = baseRepository.findAll(pageable);

        List<OrderDetailResponse> orderDetailResponseList = orderDetails.stream()
                .map(orderDetail -> response(orderDetail))
                .collect(Collectors.toList());

        return null;
    }

    public OrderDetailResponse response (OrderDetail orderDetail){

        OrderDetailResponse body = OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .status(orderDetail.getStatus())
                .arrivalDate(orderDetail.getArrivalDate())
                .quantity(orderDetail.getQuantity())
                .totalPrice(orderDetail.getTotalPrice())
                .itemID(orderDetail.getItem().getId())
                .orderGroupID(orderDetail.getOrderGroup().getId())
                .build();

        return body;
    }

}
