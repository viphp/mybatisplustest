package com.example.mybatisplustest.entity;

import lombok.Data;

import java.util.Date;

@Data  //编译时自动生成实体类属性的getter/setter方法
public class User {
    private Long id;
    private String name;
    private String password;
    private String email;
    private int gender;
    private int deptid;
    private Date birth;
    private int age;

}
