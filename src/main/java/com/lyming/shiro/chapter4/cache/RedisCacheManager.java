package com.lyming.shiro.chapter4.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @Description : 自定义shiro缓存管理器
 * @Author : Lyming
 * @Date: 2020-09-07 17:58
 */
@Slf4j
public class RedisCacheManager implements CacheManager {
    /**
     *
     * @param cacheName 认证或者授权的名字
     * @param <K>
     * @param <V>
     * @return
     * @throws CacheException
     */
    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        log.info("缓存管理器入参:{}",cacheName);
        return new RedisCache<>(cacheName);
    }
}
