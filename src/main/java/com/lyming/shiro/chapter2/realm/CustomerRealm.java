package com.lyming.shiro.chapter2.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
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
    return null;
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
