package com.service;

import com.common.ServerResponse;
import com.pojo.Person;

import javax.servlet.http.HttpSession;

public interface PersonService {
    ServerResponse login(String username, String password, HttpSession httpSession);
    boolean isManage(Person user);
}
