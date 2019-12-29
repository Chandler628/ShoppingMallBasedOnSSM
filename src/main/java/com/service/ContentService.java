package com.service;

import com.common.ServerResponse;
import com.pojo.Content;
import com.pojo.Person;

import java.util.List;

public interface ContentService {
    ServerResponse delete(Integer id);
    List<Content> getProductList(Person user, String type);
    Content getProduct(int id, Person user);
    List<Content> account();
    int publicSubmit(Content product);
    Content findProductById(int id);
    int editSubmit(Content product);
}
