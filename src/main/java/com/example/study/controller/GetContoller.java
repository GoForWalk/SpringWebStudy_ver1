package com.example.study.controller;

import com.example.study.model.SearchParam;
import com.example.study.model.network.Header;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") // Localhost:8080/api
public class GetContoller {

    @RequestMapping(method = RequestMethod.GET, path = "/getMethod") // Localhost:8080/api/getMethod
    public String getRequest(){

        return "Hi getMethod";
    }

    @GetMapping("/getParameter") // localhost:8080/api/getParameter?id=1234&password=abcd
    public String getParameter(@RequestParam String id, @RequestParam(name = "password") String pwd){
        String password = "bbbb";

        System.out.println("id" + id + "password" + pwd);
        return id + pwd;
    }

    // get 방식
    // localhost:8080/api/getMultiParameter?account=abcd&email=study@gmail.com&page=10
    // 메소드 내에서 같은 Mapping 값을 가질 때는 오류가 발생한다. (어디로 매핑해야하는지 모름)
    @GetMapping("/getMultiParameter")
    public SearchParam getMultiParameter(SearchParam searchParam){
        System.out.println(searchParam.getAccount());
        System.out.println(searchParam.getEmail());
        System.out.println(searchParam.getPage());

        // {"account" : "" , "email" : "" , "page" : 0}
        // spring boot의 특징
        // http : json 을 표준으로 사용해서, 기본으로 json 형태로 전환한다.
        // get 을 통했을때, object 를 return 하면, 해당 object를 jackson을 통해서 json 형태로 변환하여 return

        // json parsing
        return searchParam;
    }

    @GetMapping("/header")
    public Header getHeader(){


        // {"resultCode" : "OK" , "description" : "OK"}
        return Header.builder().resultCode("OK").description("OK").build();
    }

}
