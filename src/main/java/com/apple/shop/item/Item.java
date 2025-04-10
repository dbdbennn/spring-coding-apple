package com.apple.shop.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Getter
@Setter
public class Item {
    /*
     * column 정의
    */
    // table마다 @Id 필수
    // @Id는 알아서 unique true로 설정
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    // Entity에서는 int -> Integer
    // Long은 100L과 같이 저장
    private Integer price;

}

