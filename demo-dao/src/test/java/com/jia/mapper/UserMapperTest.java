package com.jia.mapper;

import com.jia.model.entity.UserDO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: jia
 * @Date: 2018/7/26 16:27
 * @Description:
 */
public class UserMapperTest extends BaseTest{

    @Autowired
    UserMapper userMapper;

    @Test
    @Transactional
    public void insert() {
        UserDO userDO = new UserDO();
        userDO.setAccount("lisi");
        userDO.setPassword("123456");
        userDO.setSalt("123456");
        userDO.setName("李四");
        userDO.setAge(20);
        userMapper.insert(userDO);
    }

    @Test
    public void selectAll() {
        List<UserDO> userDOS = userMapper.selectAll();
        Assert.assertTrue(userDOS != null);
    }

    @Test
    public void selectByParam() {
    }

    @Test
    public void selectByAccount() {
    }

    @Test
    public void countByParam() {
    }
}