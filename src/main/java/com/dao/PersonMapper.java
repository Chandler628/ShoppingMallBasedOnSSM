package com.dao;

import com.pojo.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PersonMapper {
    int checkUsername(String username);

    Person login(@Param("username") String username,@Param("password") String password);
}