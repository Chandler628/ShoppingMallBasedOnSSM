package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.dao.PersonMapper;
import com.pojo.Person;
import com.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author 王哲
 */
@Service("personService")
public class PersonServiceImpl implements PersonService {
    @Resource
    private PersonMapper personMapper;
    @Override
    public ServerResponse login(String username, String password, HttpSession httpSession) {
        int resultCount = personMapper.checkUsername(username);
        if (resultCount>0){
            Person user = personMapper.login(username, password);
            if(user != null){
                httpSession.setAttribute(Const.USER, user);
                return ServerResponse.createBySuccess();
            }
        }else{
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        return ServerResponse.createByErrorMessage("密码错误");
    }

    public boolean isManage(Person user){
        return user != null && user.getUsertype() != 1;
    }
}
