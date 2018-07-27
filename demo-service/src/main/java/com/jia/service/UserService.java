package com.jia.service;

import com.jia.model.entity.UserDO;
import com.jia.model.param.UserQueryParam;

import java.util.List;

/**
 * @Auther: jia
 * @Date: 2018/7/27 14:50
 * @Description:
 */
public interface UserService {

    int insert(UserDO userDO);

    List<UserDO> findAllUser();

    List<UserDO> getByPage(UserQueryParam param, int pageNum, int pageSize);

    int countByParam(UserQueryParam param);

    UserDO getUserByName(String account);
}
