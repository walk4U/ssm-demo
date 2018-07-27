package com.jia.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: jia
 * @Date: 2018/7/27 13:50
 * @Description:
 */
public class ShiroCacheManager implements CacheManager {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private net.sf.ehcache.Cache memcache;

    private String activeSessionsCacheName;
    private String authenticationCacheName;
    private String authorizationCacheName;

    private Map<String, ShiroValueCache> cacheMap = new HashMap<>();

    public String getActiveSessionsCacheName() {
        return activeSessionsCacheName;
    }

    public void setActiveSessionsCacheName(String activeSessionsCacheName) {
        this.activeSessionsCacheName = activeSessionsCacheName;
    }

    public String getAuthenticationCacheName() {
        return authenticationCacheName;
    }

    public void setAuthenticationCacheName(String authenticationCacheName) {
        this.authenticationCacheName = authenticationCacheName;
    }

    public String getAuthorizationCacheName() {
        return authorizationCacheName;
    }

    public void setAuthorizationCacheName(String authorizationCacheName) {
        this.authorizationCacheName = authorizationCacheName;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        ShiroValueCache cache;
        if (StringUtils.equals(activeSessionsCacheName, name)) {
            cache = new ShiroValueCache<K,V>(name, memcache,(RedisTemplate<K, V>)redisTemplate,true);
        } else {
            cache = new ShiroValueCache<K,V>(name, memcache,  (RedisTemplate<K, V>)redisTemplate,false);
        }
        cacheMap.put(name, cache);
        return cache;
    }

    public ShiroValueCache getTargetCache(String name) {
        return cacheMap.get(name);
    }
}
