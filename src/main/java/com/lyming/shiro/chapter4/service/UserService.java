package com.lyming.shiro.chapter4.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lyming.shiro.chapter4.entity.Perms;
import com.lyming.shiro.chapter4.entity.Role;
import com.lyming.shiro.chapter4.entity.User;

import java.util.List;

/**
 * @Description :
 * @Author : Lyming
 * @Date: 2020-09-06 19:11
 */
public interface UserService {
    void registerUser(User user) throws JsonProcessingException;

    User findByUsername(String username);

    User findRolesByUsername(String username);
    //根据角色id查询权限集合
    List<Perms> findPermsByRoleId(Integer id);
}
