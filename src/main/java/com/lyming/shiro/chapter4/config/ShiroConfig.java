package com.lyming.shiro.chapter4.config;

import com.lyming.shiro.chapter4.cache.RedisCacheManager;
import com.lyming.shiro.chapter4.realm.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * description:shiro 配置
 *
 * @author lyming
 * @date 2020/9/6 12:11 上午
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("/user/login","anon");
        map.put("/user/register","anon");
        map.put("/register.jsp","anon");
        map.put("/user/getCaptcha","anon");
        map.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("customRealm") Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    @Bean(name = "customRealm")
    public Realm getRealm(){
        CustomRealm customRealm = new CustomRealm();
        //修改密码校验匹配器
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为md5
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        //开启缓存管理
        customRealm.setCacheManager(new RedisCacheManager());
        //开启全局缓存
        customRealm.setCachingEnabled(true);
        //认证缓存
        customRealm.setAuthenticationCacheName("authenticationCache");
        customRealm.setAuthenticationCachingEnabled(true);
        //授权缓存
        customRealm.setAuthorizationCacheName("authorizationCache");
        customRealm.setAuthorizationCachingEnabled(true);
        return customRealm;
    }
}
