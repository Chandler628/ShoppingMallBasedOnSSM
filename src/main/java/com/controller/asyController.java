package com.controller;

import com.common.Const;
import com.common.ServerResponse;
import com.pojo.Person;
import com.service.ContentService;
import com.service.PersonService;
import com.service.TrxService;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author wangzhe
 */
@Controller
@RequestMapping("/api")
public class asyController {
    @Resource
    private PersonService personService;

    @Resource
    private ContentService contentService;

    @Resource
    private TrxService trxService;

    public static final Logger log = LogManager.getLogger(asyController.class);

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse login(@RequestParam("userName") String username,@RequestParam("password") String password, HttpSession httpSession){
/**
 * 在代码调试阶段此处发现第一个错误 在此处增加注释 用于记录
 * 错误现象 点击登录时出现提示无法登录
 * 解决过程 发现前端pageLogin.js页面用于传递用户名的参数为userName 而在后端接受该参数使用的参数名为username 随即更正
 * 调试结果 正常登录
 */

        log.debug("------------hello log4j------------");
        return personService.login(username,password,httpSession);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
//    @SessionAttribute(name = Const.USER) Person user,
    public ServerResponse delete(@SessionAttribute(name = Const.USER) Person user, @RequestParam("id") Integer id){
        if (personService.isManage(user)){
            return ServerResponse.createByError();
        }else{
            return contentService.delete(id);
    }
    }

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse buy(@Param("id") Number id, @SessionAttribute(name = Const.USER) Person user){
//        Person user = new Person();
//        user.setId(1);
        return trxService.buy(user,id.intValue(), new Date().getTime());
    }
}

