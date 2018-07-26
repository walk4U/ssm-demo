package com.jia.mapper;

import com.jia.model.entity.UserDO;
import com.jia.model.param.UserQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    int insert(UserDO userDO);

    List<UserDO> selectAll();

    List<UserDO> selectByParam(UserQueryParam param);

    UserDO selectByAccount(String account);

    int countByParam(UserQueryParam param);
}
