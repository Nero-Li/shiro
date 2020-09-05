package com.lyming.shiro.chapter3.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/** @Description : @Author : Lyming @Date: 2020-08-30 19:12 */
public class CustomerRealm extends AuthorizingRealm {
  /**
   * 授权
   *
   * @param principals
   * @return
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    System.out.println("==================================");
    String primaryPrincipal = (String) principals.getPrimaryPrincipal();
    System.out.println("身份信息:" + primaryPrincipal);
    //根据身份信息(用户名)获取当前用户的角色信息以及权限信息
    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    //将数据库中查询的角色信息赋值给权限对象
    simpleAuthorizationInfo.addRole("admin");
    simpleAuthorizationInfo.addRole("user");
    // 将数据库查询的权限信息赋值给权限对象
    simpleAuthorizationInfo.addStringPermission("user:*:01");
    simpleAuthorizationInfo.addStringPermission("product:create");
    return simpleAuthorizationInfo;
  }

  /**
   * 认证
   *
   * @param token
   * @return
   * @throws AuthenticationException
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    if ("zhangsan".equals(token.getPrincipal())) {
      return new SimpleAuthenticationInfo("zhangsan",
              "f7a06fb6ce808651c39eba2e289a0c23",
              ByteSource.Util.bytes("!@#$"),this.getName());
    }
    return null;
  }

  public static void main(String[] args) {
      //202cb962ac59075b964b07152d234b70
//    Md5Hash md5 = new Md5Hash("123");
      //db4b043f7b97a5935266c3a092a37759
//    Md5Hash md5 = new Md5Hash("123","!@#$");
      //f7a06fb6ce808651c39eba2e289a0c23
    Md5Hash md5 = new Md5Hash("123","!@#$",1024);
    System.out.println(md5.toHex());
  }
}
