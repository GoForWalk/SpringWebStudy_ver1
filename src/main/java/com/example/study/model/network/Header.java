package com.example.study.model.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// @JsonInclude // 추가가능
public class Header<T> {
    // T : Generic

    // API 통신 시간
    // 이렇게 해서 출력 형태를 snack case 로 바꾸는 방법(하나하나 따로 직접 설정)
    @JsonProperty("transaction_time")
    private String transactionTime; // ISO , YYYY-MM-DD HH:mm:ss
    // / application.properties 에서 설정하는 방법 (일괄적으로 가능) : spring.jackson.property-naming-strategy=SNAKE_CASE

    // API 응답 코드
    private String resultCode;

    // api 부가 설명
    private String description;

    // api 제작시 snake case로 제작  / java는 camel case 이므로 출력 형태를 변경해야 한다.

    private T data;


    // OK
    public static <T> Header<T> Ok(){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now().toString())
                .resultCode("OK")
                .description("OK")
                .build();
    }

    // DATA OK
    public static <T> Header<T> Ok(T data){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now().toString())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .build();
    }

    // ERROR
    public static <T> Header<T> ERROR(String description){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now().toString())
                .resultCode("ERROR")
                .description(description)
                .build();
    }

}
