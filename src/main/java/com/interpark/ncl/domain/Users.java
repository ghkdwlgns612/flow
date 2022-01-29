package com.interpark.ncl.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {
    @Id
    private String userNo;

    private String gender;

    private Integer age;

    @Builder
    public Users(String userNo, String gender, Integer age) {
        this.userNo = userNo;
        this.gender = gender;
        this.age = age;
    }

}
