package com.example.study.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryType {

    NOTEBOOK(0),
    DESKTOP(1),
    PHONE(2),
    TABLET(3),
    EARPHONE(4),
    MOUSE(5),
    KEYBOARD(6)
    ;

     private Integer id;
}
