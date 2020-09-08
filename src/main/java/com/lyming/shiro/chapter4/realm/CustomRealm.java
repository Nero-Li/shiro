package com.lyming.shiro.chapter4.realm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyming.shiro.chapter4.entity.Perms;
import com.lyming.shiro.chapter4.entity.Role;
import com.lyming.shiro.chapter4.entity.User;
import com.lyming.shiro.chapter4.service.UserService;
import com.lyming.shiro.chapter4.source.MyByteSource;
import com.lyming.shiro.chapter4.utils.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * description:
 *
 * @author lyming
 * @date 2020/9/6 12:33 上午
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("===================开始授权========================");
        String primaryPrincipal = (String)principalCollection.getPrimaryPrincipal();
        //根据主身份信息获取角色和权限
        UserService userServiceImpl = (UserService) ApplicationContextUtil.getBean("userServiceImpl");
        User user = userServiceImpl.findRolesByUsername(primaryPrincipal);
        ObjectMapper mapper = new ObjectMapper();
        if (CollectionUtils.isNotEmpty(user.getRoles())) {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            user.getRoles().forEach(role->{
                //角色信息
                simpleAuthorizationInfo.addRole(role.getName());
                //权限信息
                List<Perms> perms = userServiceImpl.findPermsByRoleId(role.getId());
                if (CollectionUtils.isNotEmpty(perms)) {
                    perms.forEach(perm->{
                        simpleAuthorizationInfo.addStringPermission(perm.getName());
                    });
                }
            });
            try {
                log.info("用户授权信息:{}", mapper.writeValueAsString(simpleAuthorizationInfo));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                log.error("用户授权信息转换成json字符串异常",e);
            }
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("===================开始认证========================");
        //在工厂中获取service对象
        UserService userServiceImpl = (UserService) ApplicationContextUtil.getBean("userServiceImpl");
        //获取身份信息
        String principal = (String) authenticationToken.getPrincipal();
        User user = userServiceImpl.findByUsername(principal);
        if (!ObjectUtils.isEmpty(user)) {
            log.info("===================认证成功========================");
            return new SimpleAuthenticationInfo(principal, user.getPassword(), new MyByteSource(user.getSalt()), this.getName());
        }
        log.info("===================认证失败========================");
        return null;
    }
}
