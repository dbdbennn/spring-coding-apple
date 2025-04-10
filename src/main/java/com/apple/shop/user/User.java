package com.apple.shop.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name = "홍길동";
    private Integer age = 10;

    void addOneAge() {
        age += 1;
    }

    public void setAge(Integer age) {
        if(0 < age && age < 100) {
            this.age = age;
        }
    }
}
