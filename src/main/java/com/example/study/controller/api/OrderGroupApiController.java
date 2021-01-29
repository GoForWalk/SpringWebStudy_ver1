package com.example.study.controller.api;

import com.example.study.controller.CrudController;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.network.request.OrderGroupApiRequest;
import com.example.study.model.network.response.OrderGroupApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/orderGroup")
public class OrderGroupApiController extends CrudController<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {

//    @Autowired
//    private OrderGroupApiLogicService orderGroupApiLogicService;
//
//    @PostConstruct
//    public void init(){
//        this.baseService = orderGroupApiLogicService;
//    }

//    @Override
//    @PostMapping("")
//    public Header<OrderGroupApiResponse> create(@RequestBody Header<OrderGroupApiRequest> request) {
//        log.info("OrderGroup create request : {}", request);
//        return orderGroupApiLogicService.create(request);
//    }
//
//    @Override
//    @GetMapping("{id}")
//    public Header<OrderGroupApiResponse> read(@PathVariable Long id) {
//        log.info("OrderGroup read request : {}", id);
//        return orderGroupApiLogicService.read(id);
//    }
//
//    @Override
//    @PutMapping("")
//    public Header<OrderGroupApiResponse> update(@RequestBody Header<OrderGroupApiRequest> request) {
//        log.info("OrderGroup update request : {}", request);
//        return orderGroupApiLogicService.update(request);
//    }
//
//    @Override
//    @DeleteMapping("{id}")
//    public Header delete(@PathVariable Long id) {
//        log.info("OrderGroup delete request : {}", id);
//        return orderGroupApiLogicService.delete(id);
//    }
}
