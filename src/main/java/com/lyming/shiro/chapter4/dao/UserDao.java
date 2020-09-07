package com.lyming.shiro.chapter4.dao;

import com.lyming.shiro.chapter4.entity.Perms;
import com.lyming.shiro.chapter4.entity.Role;
import com.lyming.shiro.chapter4.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description :
 * @Author : Lyming
 * @Date: 2020-09-06 19:08
 */

@Repository
public interface UserDao {

    void saveUser(User user);

    User findByUsername(String username);

    User findRolesByUsername(String username);

    //根据角色id查询权限集合
    List<Perms> findPermsByRoleId(Integer id);
}
