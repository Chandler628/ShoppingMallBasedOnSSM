package com.controller;

import com.common.Const;
import com.pojo.Content;
import com.pojo.Person;
import com.service.ContentService;
import com.service.PersonService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author wangzhe
 */
@Controller
public class MainController {

    @Resource
    private ContentService contentService;

    @Resource
    private PersonService personService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpSession session, @RequestParam(name = "type",required = false) String type){
        /**
         * 在代码调试阶段此处发现第二个错误 在此处增加注释 用于记录
         * 错误现象 前端页面中无法显示用户信息例如 卖家你好，seller！[退出] 首页 发布
         * 解决过程 之前认为前端获取用户参数应直接从session中取出即可，后经请教后得知前端是从model中得到用户信息。随即在后端添加代码用于将数据从session取出并存放在model中
         * 调试结果 前端正常回显
         */
        //从session中取出用户登录信息
        Person user = (Person) session.getAttribute(Const.USER);
        List<Content> productList = contentService.getProductList(user,type);

        //将用户登录信息存储到model中预处理
        model.addAttribute(Const.USER, user);
        model.addAttribute(Const.PRODUCT_LIST, productList);



        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpSession httpSession){
        Person person = (Person) httpSession.getAttribute(Const.USER);
        if (person != null){
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/login";
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(@RequestParam("id") int id, Model model, @SessionAttribute(value = Const.USER, required = false) Person user){
        Content product = contentService.getProduct(id, user);
        model.addAttribute(Const.PRODUCT,product);
        return "show";
        /**
         * 在代码调试阶段此处发现第四个错误 在此处增加注释 用于记录
         * 错误现象 在前端的展示页面报错 报错内容为freemarker的model中无法获取isBuy之类的云云
         * 解决过程 苦寻无果 最后发现pojo类中的isBuy属性中定义的get方法方法名为getBuy而且方法的作用范围为private 随机将其修改
         * 调试结果 页面回显正常
         */
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account(Model model, @SessionAttribute(Const.USER) Person user){
        List<Content> contentList = contentService.account();
        if (user.getUsertype()==0) {
            model.addAttribute(Const.BUY_LIST, contentList);
        }
        return "account";
    }

    @RequestMapping(value = "/public",method = RequestMethod.GET)
    public String publicPage(@SessionAttribute(Const.USER) Person user) {
        if (user == null || user.getUsertype() == 0) {
            return "redirect:/";
        }
        return "public";
    }

    @RequestMapping(value = "/publicSubmit", method = RequestMethod.POST)
    public String publicSubmit(Content product,
                               @SessionAttribute(Const.USER) Person user, Model model){
        /**
         * 在代码调试阶段此处发现第三个错误 在此处增加注释 用于记录
         * 错误现象 点击发布产品报500错误，发布之后点击查看内容时显示的时上一个发布的产品
         * 解决过程 后端接受的参数多了一个id 随即删除，应使用的sql语句为max(id),但该商品未发布时应商品表没有该数据，随即改为max(id)+1
         * 调试结果 正常发布
         */
        if (personService.isManage(user)){
            return "redirect:/";
        }
        if (contentService.publicSubmit(product) > 0){
            model.addAttribute(Const.PRODUCT, product);
        }
        return "publicSubmit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@Param("id") int id, Model model){
        Content product = contentService.findProductById(id);
        model.addAttribute(Const.PRODUCT, product);
        return "edit";
    }

    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public String editSubmit(Content product,
                             @SessionAttribute(Const.USER) Person user, Model model){
        /**
         * 在代码调试阶段此处发现第五个错误 在此处增加注释 用于记录
         * 错误描述 点击编辑提交结果重新发布了一个新的产品
         * 解决过程 因之前写代码为了方便直接调用了publicSubmit的service层 重新写新的service层代码
         * 调试结果 正常回显
         */
        if (personService.isManage(user)){
            return "redirect:/";
        }
        if (contentService.editSubmit(product) > 0){
            model.addAttribute(Const.PRODUCT, product);
        }
        return "editSubmit";
    }

}
