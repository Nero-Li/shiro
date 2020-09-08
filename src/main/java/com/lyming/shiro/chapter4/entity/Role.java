package com.lyming.shiro.chapter4.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Description :
 * @Author : Lyming
 * @Date: 2020-09-07 15:48
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    private Integer id;
    private String name;

    //权限集合
    private List<Perms> perms;
}
