package com.dao;

import com.pojo.Content;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Component
public interface ContentMapper {

    int delete(@Param("id") Integer id);

    Content selectById(@Param("id") int id);

    List<Content> getContentList();

    Content getProduct(@Param("id") int id);

    List<Content> account();

    @Insert("INSERT INTO content(price,title,image,summary,detail) "+"VALUE (#{price},#{title},#{image},#{detail},#{summary})")
    int insertProduct(@Param("price") BigDecimal price, @Param("title") String title,@Param("image") String image,
                      @Param("detail") String detail,@Param("summary") String summary);

    @Update("UPDATE content SET price = #{price}, title = #{title}, image = #{image}, detail = #{detail}, summary = #{summary}" +
            "WHERE id = #{id}")
    int updateProduct(@Param("price") BigDecimal price, @Param("title") String title, @Param("image") String image,
                      @Param("detail") String detail, @Param("summary") String summary, @Param("id") int id);

    int lastInsertId();

    int count();
}