package com.lyming.shiro.chapter3;

import com.lyming.shiro.chapter3.realm.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

/** @Description : @Author : Lyming @Date: 2020-08-30 19:14 */
public class TestCustomerAuthentication {
  public static void main(String[] args) {
    // 创建SecurityManager
    DefaultSecurityManager securityManager = new DefaultSecurityManager();
    //设置自定义realm
    CustomerRealm customerRealm = new CustomerRealm();
    HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
    hashedCredentialsMatcher.setHashAlgorithmName("md5");
    hashedCredentialsMatcher.setHashIterations(1024);
    customerRealm.setCredentialsMatcher(hashedCredentialsMatcher);
    securityManager.setRealm(customerRealm);
    SecurityUtils.setSecurityManager(securityManager);
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","123");
    //  用户认证
    try {
      System.out.println("认证状态:" + subject.isAuthenticated());
      subject.login(token);
      System.out.println("认证状态:" + subject.isAuthenticated());
    } catch (UnknownAccountException e) {
      e.printStackTrace();
      System.out.println("认证失败,用户名不存在");
    } catch (IncorrectCredentialsException e) {
      e.printStackTrace();
      System.out.println("认证失败,密码错误");
    }

    // 认证用户进行授权
    if (subject.isAuthenticated()) {
      //基于单角色的权限控制
      System.out.println(subject.hasRole("admin"));
      System.out.println(subject.hasRole("super"));
      System.out.println(subject.hasRole("user"));
      // 基于多角色的权限控制
      System.out.println(subject.hasAllRoles(Arrays.asList("admin", "user")));
      // 是否具有其中一个角色
      boolean[] booleans = subject.hasRoles(Arrays.asList("admin", "super", "user"));
      for (boolean aBoolean : booleans) {
        System.out.println(aBoolean);
      }
      System.out.println("----------------------------");
      // 基于权限字符串的访问控制 资源标识符:操作:资源实例
      System.out.println("权限"+subject.isPermitted("user:*:01"));
      System.out.println("权限"+subject.isPermitted("product:create:02"));
      // 分别具有哪些权限
      boolean[] permitted = subject.isPermitted("user:*:01", "order:*");
      for (boolean b : permitted) {
        System.out.println(b);
      }
      // 同时具有哪些权限
      System.out.println(subject.isPermittedAll("user:*:01", "product:create"));

    }
  }
}
