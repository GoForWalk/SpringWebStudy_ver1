package com.example.study.service;

import com.example.study.model.entity.Item;
import com.example.study.model.entity.Partner;
import com.example.study.model.enumclass.ItemStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemApiLogicService extends BaseService<ItemApiRequest, ItemApiResponse, Item> {

    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {

        // request 가 Null 아 아닐 때,

        // 1. item 불러오기 / 객체 초기화/ 담기
        ItemApiRequest itemApiRequest = request.getData(); // TODO Optional로 변환
//        Optional<ItemApiRequest> itemApiRequest = Optional.of(request.getData()); // TODO Optional로 변환

        Item item = Item.builder()
                .status(ItemStatus.REGISTERED)
                .name(itemApiRequest.getName())
                .title(itemApiRequest.getTitle())
                .content(itemApiRequest.getContent())
                .price(itemApiRequest.getPrice())
                .brandName(itemApiRequest.getBrandName())
                .registeredAt(LocalDateTime.now())
                .partner(partnerRepository.getOne(itemApiRequest.getPartnerId()))
                .build();

        Item newItem = baseRepository.save(item);

       return Header.Ok(response(newItem));
//        // code refactoring
//
//
//        // 2. itemRepository 사용해서 새로 입력하기
//        Item newItem = baseRepository.save(item);
//
//        // 3. api response 보내기
//        return Header.Ok(response(newItem));
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {

        // 1. itemRepository, findbyid
       return baseRepository.findById(id).map(item->Header.Ok(response(item))).orElseGet(()->Header.ERROR("NO DATA"));
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {

        ItemApiRequest itemApiRequest = request.getData();

        return baseRepository.findById(itemApiRequest.getId()).map(item -> {

            item.setStatus(itemApiRequest.getStatus())
                    .setName(itemApiRequest.getName())
                    .setTitle(itemApiRequest.getTitle())
                    .setStatus(itemApiRequest.getStatus())
                    .setContent(itemApiRequest.getContent())
                    .setPrice(itemApiRequest.getPrice())
                    .setBrandName(itemApiRequest.getBrandName())
                    .setRegisteredAt(itemApiRequest.getRegisteredAt())
                    .setUnregisteredAt(itemApiRequest.getUnregisteredAt())
                    ;
            return item;

        }).map(item -> baseRepository.save(item))
                .map(newItem -> Header.Ok(response(newItem)))
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id).map(item -> {baseRepository.delete(item); return Header.Ok();}).orElseGet(()->Header.ERROR("NO DATA"));
    }

    @Override
    public Header<List<ItemApiResponse>> search(Pageable pageable) {

        Page<Item> items = baseRepository.findAll(pageable);

        List<ItemApiResponse> itemApiResponseList = items.stream()
                .map(item -> response(item))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .currentElements(items.getNumberOfElements())
                .currentPage(items.getNumber())
                .totalPage(items.getTotalPages())
                .totalElements(items.getTotalElements())
                .build();

        return Header.Ok(itemApiResponseList, pagination);
    }

    public ItemApiResponse response(Item item){
        // item -> itemApiResponse

//        String statusTitle  = item.getStatus().getTitle(); // enum 의 설정에 있는 id

        ItemApiResponse userApiResponse = ItemApiResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .name(item.getName())
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .registeredAt(item.getRegisteredAt())
                .unregisteredAt(item.getUnregisteredAt())
                .partnerId(item.getPartner().getId())
                .build();

        // Header + data
        return userApiResponse;
    }
}