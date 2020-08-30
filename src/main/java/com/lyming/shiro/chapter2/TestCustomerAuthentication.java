package com.lyming.shiro.chapter2;

import com.lyming.shiro.chapter2.realm.CustomerRealm;
import org.apache.shiro.mgt.DefaultSecurityManager;

/** @Description : @Author : Lyming @Date: 2020-08-30 19:14 */
public class TestCustomerAuthentication {
  public static void main(String[] args) {
    // 创建SecurityManager
    DefaultSecurityManager securityManager = new DefaultSecurityManager();
    //设置自定义realm
    securityManager.setRealm(new CustomerRealm());
  }
}
