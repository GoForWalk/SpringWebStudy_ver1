package com.example.study.service;

import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, User> {

//    @Autowired
//    private UserRepository userRepository;

    // 1. request data
    // 2. User 생성
    // 3. 생성된 데이터 -> UserApiResponse return

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
       return response(newUser);
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
        return baseRepository.findById(id).map(user -> response(user)).orElseGet(()->Header.ERROR("데이터 없음"));
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
                .map(updateUser -> response(updateUser))    // 4. userApiResponse
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

    private Header<UserApiResponse> response(User user){
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
        return Header.Ok(userApiResponse);
    }
}
