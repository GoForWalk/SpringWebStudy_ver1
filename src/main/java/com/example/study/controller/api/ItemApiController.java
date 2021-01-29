package com.example.study.controller.api;

import com.example.study.controller.CrudController;
import com.example.study.model.entity.Item;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/item")
public class ItemApiController extends CrudController<ItemApiRequest, ItemApiResponse, Item> {
//
//    @Autowired
//    private ItemApiLogicService itemApiLogicService;
//
//    @PostConstruct
//    public void init(){
//        // CrudController 를 상속받고, 상속 받을때,
//        // baseService 를 주입받은 itemApiLogicService 와 연결시킨다.
//        // 따라서, CrudController 에서 baseService 라고 설정된 부분들은 이 method 로 인해서,
//        // itemApiLogicService 처럼 작동한다.
//        this.baseService = itemApiLogicService;
//    }

//
//    @Override
//    @PostMapping("") // api/item
//    public Header<ItemApiResponse> create(@RequestBody Header<ItemApiRequest> request) {
//
//        log.info("create request: {}", request);
//        return itemApiLogicService.create(request);
//    }
//
//    @Override
//    @GetMapping("{id}") // api/item/1 ... 100...
//    public Header<ItemApiResponse> read(@PathVariable Long id) {
//        log.info("read id : {}", id);
//        return itemApiLogicService.read(id);
//    }
//
//    @Override
//    @PutMapping("") // api/item
//    public Header<ItemApiResponse> update(@RequestBody Header<ItemApiRequest> request) {
//        log.info("update request : {}", request);
//        return itemApiLogicService.update(request);
//    }
//
//    @Override
//    @DeleteMapping("{id}") // api/item/1...
//    public Header<ItemApiResponse> delete(@PathVariable Long id) {
//        log.info("delete : {}", id);
//        return itemApiLogicService.delete(id);
//    }
}
