package com.jia.shiro;

import net.sf.ehcache.Element;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: jia
 * @Date: 2018/7/27 13:54
 * @Description:
 */
public class ShiroValueCache<K,V> implements Cache<K,V>,Serializable {

    private static final long serialVersionUID = 5492657231499634822L;

    private static final String REDIS_SHIRO_CACHE = "shiro-cache:";
    private String cacheKeyPrefix;
    private long globExpire = 24 * 60; // expired after 1 day
    private boolean useMemcache;

    // L1 cache
    private net.sf.ehcache.Cache memcache;

    // L2 cache
    private RedisTemplate<K, V> redisTemplate;

    public ShiroValueCache(String name, net.sf.ehcache.Cache memcache,
                           RedisTemplate<K, V> redisTemplate, boolean useMemcache ) {

        this.cacheKeyPrefix = REDIS_SHIRO_CACHE + name + ":";
        this.redisTemplate = redisTemplate;
        this.memcache = memcache;
        this.useMemcache = useMemcache;
    }

    @Override
    public V get(K key) throws CacheException {
        // get cache key
        K cachekey = getCacheKey(key);
        if (useMemcache) {
            // try to get from L1 cache
            Element value = memcache.get(cachekey);
            if (value != null) {
                return (V)value.getObjectValue();
            }
        }

        // try to get from L2 cache
        redisTemplate.boundValueOps(cachekey).expire(globExpire, TimeUnit.MINUTES);
        V val = redisTemplate.boundValueOps(cachekey).get();

        if (useMemcache) {
            // save in L1 cache
            memcache.put(new Element(cachekey, val));
        }

        return val;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        // get old value
        V old = get(key);

        // get cache key
        K cachekey = getCacheKey(key);

        if (useMemcache) {
            // save in L1 cache
            memcache.put(new Element(cachekey, value));
        }

        // save in L2 cache
        redisTemplate.boundValueOps(cachekey).set(value,globExpire,TimeUnit.MINUTES);

        // return old value
        return old;
    }

    @Override
    public V remove(K key) throws CacheException {
        // get old value
        V old = get(key);

        // get cache key
        K cachekey = getCacheKey(key);

        if (useMemcache) {
            // delete from L1 cache
            memcache.remove(cachekey);
        }

        // delete from L2 cache
        redisTemplate.delete(cachekey);

        // return old value
        return old;
    }

    @Override
    public void clear() throws CacheException {
        if (useMemcache) {
            memcache.removeAll();
        }
    }

    public void clearMemoryCache(String key) {
        if (useMemcache) {
            memcache.remove(getCacheKey(key));
        }
    }

    @Override
    public int size() {
        if(keys() == null)
            return 0;
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    private K getCacheKey(Object k) {
        return (K)(this.cacheKeyPrefix + k);
    }
}
