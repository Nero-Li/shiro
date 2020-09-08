package com.lyming.shiro.chapter4.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description :
 * @Author : Lyming
 * @Date: 2020-09-07 15:49
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Perms implements Serializable {

    private Integer id;
    private String name;
    private String url;
}
