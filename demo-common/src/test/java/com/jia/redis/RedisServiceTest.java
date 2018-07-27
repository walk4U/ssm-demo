package com.jia.redis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: jia
 * @Date: 2018/7/27 10:42
 * @Description:
 */
public class RedisServiceTest extends BaseTest {

    @Autowired
    RedisService redisService;

    @Test
    public void set() {
        boolean result = redisService.set("ssm", "hello ssm-demo", 600L);
        System.out.println(result);
    }

    @Test
    public void set1() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void removePattern() {
    }

    @Test
    public void remove1() {
    }

    @Test
    public void exists() {
    }

    @Test
    public void get() {
        Object ssm = redisService.get("ssm");
        System.out.println(ssm);
    }

    @Test
    public void hmSet() {
    }

    @Test
    public void hmGet() {
    }

    @Test
    public void lPush() {
    }

    @Test
    public void lRange() {
    }

    @Test
    public void add() {
    }

    @Test
    public void setMembers() {
    }

    @Test
    public void zAdd() {
    }

    @Test
    public void rangeByScore() {
    }
}