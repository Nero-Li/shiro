package com.lyming.shiro.chapter4.controller;

import com.lyming.shiro.chapter4.entity.User;
import com.lyming.shiro.chapter4.service.UserService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description :
 * @Author : Lyming
 * @Date: 2020-09-06 18:33
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
            return "redirect:/index.jsp";
        } catch (UnknownAccountException e) {
            log.error("用户名错误");
            e.printStackTrace();
        } catch (IncorrectCredentialsException e) {
            log.error("密码错误");
            e.printStackTrace();
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login.jsp";
    }

    @PostMapping("/register")
    public String register(User user){
        try {
            userService.registerUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("注册失败");
            return "redirect:/register.jsp";
        }
        return "redirect:/login.jsp";
    }
}
