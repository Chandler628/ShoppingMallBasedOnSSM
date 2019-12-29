package com.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Mapper
@Component
public interface TrxMapper {
    @Insert("INSERT INTO trx(contentId, personId, price, time) "+"VALUE (#{productId}, #{userId}, #{price}, #{time} )")
    int buy(@Param("productId") int productId, @Param("userId") int userId, @Param("price") BigDecimal price, @Param("time") long time);
}