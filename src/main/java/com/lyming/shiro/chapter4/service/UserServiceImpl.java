package com.lyming.shiro.chapter4.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyming.shiro.chapter4.dao.UserDao;
import com.lyming.shiro.chapter4.entity.Perms;
import com.lyming.shiro.chapter4.entity.Role;
import com.lyming.shiro.chapter4.entity.User;
import com.lyming.shiro.chapter4.utils.SaltUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description :
 * @Author : Lyming
 * @Date: 2020-09-06 19:12
 */
@Service("userServiceImpl")
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public void registerUser(User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String salt = SaltUtil.getSalt(8);
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt,1024);
        user.setSalt(salt);
        user.setPassword(md5Hash.toHex());
        log.info("保存用户入参:{}", mapper.writeValueAsString(user));
        userDao.saveUser(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findRolesByUsername(String username) {
        return userDao.findRolesByUsername(username);
    }

    @Override
    public List<Perms> findPermsByRoleId(Integer id) {
        return userDao.findPermsByRoleId(id);
    }
}
