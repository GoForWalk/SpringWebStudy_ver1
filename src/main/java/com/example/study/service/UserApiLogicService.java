package com.example.study.service;

import com.example.study.model.entity.Item;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.model.network.response.UserOrderInfoApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.OrderGroupRepository;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, User> {

//    @Autowired
//    private UserRepository userRepository;

    // 1. request data
    // 2. User 생성
    // 3. 생성된 데이터 -> UserApiResponse return

    @Autowired
    private OrderGroupApiLogicService orderGroupApiLogicService;

    @Autowired
    private ItemApiLogicService itemApiLogicService;

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        // 1. request data
        UserApiRequest userApiRequest = request.getData();

        // 2. User 생성 / Entity 객체 생성
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();

        User newUser = baseRepository.save(user); // BaseService 안에 BaseRepository 를 사용

        // 3. 생성된 데이터 -> userApiResponse return
        // response method 에서 처리하고 response(newUser)를 리턴
       return Header.Ok(response(newUser));
    }

    @Override
    public Header<UserApiResponse> read(Long id) {

//        // id -> repository getOne, getById
//        Optional<User>  user = userRepository.findById(id);
//
//        // user -> userApiResponse return
//       return  user
//               .map(userInfo -> response(userInfo)) // map : 다른 return 형태로 바꾸는 것
//               .orElseGet( // 유저가 없다면,
//                       ()->Header.ERROR("데이터 없음") // null 일 경우, Header 에 에러문으로 "데이터 없음" 을 입력하여 return
//               );
        // 위 주석을 한줄로 표현
        return baseRepository.findById(id).map(user -> Header.Ok(response(user))).orElseGet(()->Header.ERROR("NO DATA"));
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {

        // 1. data 가져오기
        UserApiRequest userApiRequest = request.getData();

        // 2. id를 가지고 -> user data 찾기
        Optional<User> optional = baseRepository.findById(userApiRequest.getId());

        return optional.map(user -> {

            // 3. update
            // id
            user.setAccount(userApiRequest.getAccount())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setPassword(userApiRequest.getPassword())
                    .setEmail(userApiRequest.getEmail())
                    .setStatus(userApiRequest.getStatus())
                    .setRegisteredAt((userApiRequest.getRegisteredAt()))
                    .setUnregisteredAt(userApiRequest.getUnRegisteredAt())
                    ;
            return user;
        })
                .map(user -> baseRepository.save(user))     // update -> newUser
                .map(updateUser -> response(updateUser)) // 4. userApiResponse
                .map(Header::Ok) // Header 로 감싸기
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {

        // 1. id -> repository -> user
        Optional<User> optional = baseRepository.findById(id);

        // 2. repository -> delete
        return optional.map(user -> {
            baseRepository.delete(user);
            return Header.Ok();
        }).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    public UserApiResponse response(User user){
        // user -> userApiResponse

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword()) // todo 암호화, 길이
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

        // Header + data
        return userApiResponse;
    }

    // pagination
    public Header<List<UserApiResponse>> search(Pageable pageable){

        Page<User> users = baseRepository.findAll(pageable);

        // List<UserApiResponse>
        List<UserApiResponse> userApiResponseList = users.stream()
                .map(user -> response(user))
                .collect(Collectors.toList());

        // Header 에 페이징 정보 추가
        Pagination pagination = Pagination.builder()
                .totalPage(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .currentPage(users.getNumber())
                .currentElements(users.getNumberOfElements())
                .build();

        // Header<List<UserApiResponse>>
        return Header.Ok(userApiResponseList, pagination);
    }

    // UserOrderInfoApiResponse
    public Header<UserOrderInfoApiResponse> orderInfo(Long id){

        // user
        User user = baseRepository.getOne(id);

        UserApiResponse userApiResponse = response(user);

        // orderGroup
        List<OrderGroup> orderGroupList = user.getOrderGroupList();

        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroupList.stream()
                .map(orderGroup -> {
                   OrderGroupApiResponse orderGroupApiResponse =  orderGroupApiLogicService.response(orderGroup);

                   // item api response
                    List<ItemApiResponse> itemApiResponseList = orderGroup.getOrderDetailList().stream()
                            .map(detail -> detail.getItem())
                            .map(item -> itemApiLogicService.response(item))
                            .collect(Collectors.toList());

                    orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);
                    return orderGroupApiResponse;
                })
                .collect(Collectors.toList());

        userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);

        UserOrderInfoApiResponse userOrderInfoApiResponse = UserOrderInfoApiResponse.builder()
                .userApiResponse(userApiResponse)
                .build();

        return Header.Ok(userOrderInfoApiResponse);
    }

}
