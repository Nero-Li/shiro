package com.lyming.shiro.chapter2;

import com.lyming.shiro.chapter2.realm.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

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
  }
}
