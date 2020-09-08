package com.lyming.shiro.chapter4.controller;

import com.lyming.shiro.chapter4.entity.User;
import com.lyming.shiro.chapter4.service.UserService;
import com.lyming.shiro.chapter4.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

    /**
     * 验证码方法
     *
     * @return
     */
    @RequestMapping("/getCaptcha")
    public void getCaptcha(HttpSession session, HttpServletResponse response) throws IOException {
        //生成验证码
        String captcha = VerifyCodeUtils.generateVerifyCode(4);
        //验证法放入session
        session.setAttribute("captcha", captcha);
        //验证码存入图片
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("image/png");
        VerifyCodeUtils.outputImage(220, 60, os, captcha);
    }

    @PostMapping("/login")
    public String login(String username, String password, String captcha, HttpSession session) {
        //比较验证码
        String rightCaptcha = (String) session.getAttribute("captcha");
        try {
            if (rightCaptcha.equalsIgnoreCase(captcha)) {
                Subject subject = SecurityUtils.getSubject();
                subject.login(new UsernamePasswordToken(username, password));
                return "redirect:/index.jsp";
            } else {
                throw new RuntimeException("验证码异常");
            }
        } catch (UnknownAccountException e) {
            log.error("用户名错误");
        } catch (IncorrectCredentialsException e) {
            log.error("密码错误");
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login.jsp";
    }

    @PostMapping("/register")
    public String register(User user) {
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
