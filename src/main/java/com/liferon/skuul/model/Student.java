package com.liferon.skuul.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * @project web-metering
 * @created by tobi on 2019-06-04
 */
@RedisHash("Student")
@Getter
@Setter
public class Student implements Serializable {

    public enum Gender {
        Male, Female
//        String value;
//
//        private Gender(String value) {
//            this.value = value;
//        }
    }

    private String id;
    private String name;
    private Gender gender;
    private int grade;
}
