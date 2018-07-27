package com.jia.service.impl;

import com.jia.mapper.UserMapper;
import com.jia.model.entity.UserDO;
import com.jia.model.page.PageQuery;
import com.jia.model.param.UserQueryParam;
import com.jia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: jia
 * @Date: 2018/7/27 13:50
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int insert(UserDO userDO) {
        return userMapper.insert(userDO);
    }

    @Override
    public List<UserDO> findAllUser() {
        return userMapper.selectAll();
    }

    @Override
    public List<UserDO> getByPage(UserQueryParam param, int pageNum, int pageSize) {
        PageQuery pageQuery = PageQuery.createPage(pageNum, pageSize);
        param.setStartRow(pageQuery.getStartRow());
        param.setPageSize(pageQuery.getPageSize());
        return userMapper.selectByParam(param);
    }

    @Override
    public int countByParam(UserQueryParam param) {
        return userMapper.countByParam(param);
    }

    @Override
    public UserDO getUserByName(String account) {
        return userMapper.selectByAccount(account);
    }
}
