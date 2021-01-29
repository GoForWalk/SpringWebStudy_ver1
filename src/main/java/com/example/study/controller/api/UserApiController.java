package com.example.study.controller.api;

import com.example.study.controller.CrudController;
import com.example.study.model.entity.User;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j // log 사용
@RestController
@RequestMapping("/api/user")
public class UserApiController extends CrudController<UserApiRequest, UserApiResponse, User> {

    // 추상 클래스 이용가능!!
//
//    @Autowired
//    private UserApiLogicService userApiLogicService;
//
//    @PostConstruct
//    public void init(){
//        this.baseService = userApiLogicService;
//    }

//
//    @Override
//    @PostMapping("") // api/User 에 매핑
//    public Header<UserApiResponse> create(@RequestBody Header<UserApiRequest> userApiRequest) {
//
//        log.info("{}", userApiRequest);
//        // Service -> UserApiLogicService 를 UserApiController 에 연결
//        return userApiLogicService.create(userApiRequest);
//    }
//
//    @Override
//    @GetMapping("{id}") // /api/user/{id} 로 request를 받는다.
//    public Header<UserApiResponse> read(@PathVariable(name = "id") Long id) {
//
//        log.info("read id : {}", id);
//        return userApiLogicService.read(id);
//    }
//
//    @Override
//    @PutMapping("") // /api/user
//    public Header<UserApiResponse> update(@RequestBody Header<UserApiRequest> userApiRequest) {
//
//        log.info("update request : {}", userApiRequest);
//        return userApiLogicService.update(userApiRequest);
//    }
//
//    @Override
//    @DeleteMapping("{id}") // /api/user/{id}
//    public Header delete(@PathVariable(name = "id") Long id) {
//
//        log.info("delete id : {}", id);
//        return userApiLogicService.delete(id);
//    }
}
