package com.imooc.controller;

import com.imooc.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by  OSCAR on 2018/12/19
 */
@Controller
public class UserController {

    @RequestMapping(value = "/subLogin",method = RequestMethod.POST,
    produces = "application/json;charset=utf-8")//解决return  页面乱码
    @ResponseBody
    public String login(User user){
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken  token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            token.setRememberMe(user.isRememberMe());//true为自动登录
            subject.login(token);
        }catch (Exception e){
            e.printStackTrace();
        }

        //权限校验
        if (subject.hasRole("admin")){
            return "有admin权限";
        }
        return "无admin权限";
    }

    @ResponseBody
    //@RequiresRoles("admin")//如果有admin这个角色就可以访问，没有就不能访问----改用filterChain进行过滤
    @RequestMapping(value = "/testRole",method = RequestMethod.GET)
    public String testRole(){
        return "testRole success";
    }

    @ResponseBody
    //@RequiresRoles("admin1")
    //@RequiresPermissions("admin")
    @RequestMapping(value = "/testRole1",method = RequestMethod.GET)
    public String testRole1(){
        return "testRole1 success";
    }

    @RequestMapping(value = "/testRole3", method = RequestMethod.GET)
    @ResponseBody
    public String testRole3() {
        return "testRole3 success";
    }

    @ResponseBody
    @RequestMapping(value = "/testPerms", method = RequestMethod.GET)
    public String testPerms() {
        return "testPerms success";
    }

    @ResponseBody
    @RequestMapping(value = "/testPerms1", method = RequestMethod.GET)
    public String testPerms1() {
        return "testPerms1 success";
    }

}
