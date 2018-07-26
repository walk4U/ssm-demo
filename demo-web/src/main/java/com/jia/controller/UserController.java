package com.jia.controller;

import com.google.common.collect.Lists;
import com.jia.model.entity.UserDO;
import com.jia.model.param.UserQueryParam;
import com.jia.model.result.CodeMsg;
import com.jia.model.result.Result;
import com.jia.model.vo.UserListVO;
import com.jia.model.vo.UserVO;
import com.jia.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result listUser(UserQueryParam userQueryParam, @Param("pageNum")int pageNum,
                           @Param("pageSize")int pageSize) {
        List<UserDO> userDOS = userService.getByPage(userQueryParam, pageNum, pageSize);
        List<UserVO> userVOS = Lists.newArrayList();
        for (UserDO userDO : userDOS) {
            userVOS.add(UserVO.convertToVo(userDO));
        }
        int count = userService.countByParam(userQueryParam);
        UserListVO userListVO = new UserListVO();
        userListVO.setList(userVOS);
        userListVO.setTotal(count);
        return Result.success(userListVO);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(UserDO userDO) {
        if(userDO == null || StringUtils.isBlank(userDO.getAccount())
                || StringUtils.isBlank(userDO.getPassword())
                || StringUtils.isBlank(userDO.getName())) {
            return Result.fail(CodeMsg.PARAMETER_ISNULL);
        }
        int i = userService.insert(userDO);
        if(i==1) {
            return Result.success();
        } else {
            return Result.fail(CodeMsg.USER_REGISTER_FAIL);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private Result login(String account, String password) {
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(CodeMsg.PARAMETER_ISNULL);
        }
        // 创建用户登录信息
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        token.setRememberMe(false);
        try {
            // 登录，如果失败，会有相应的异常抛出
            SecurityUtils.getSubject().login(token);
        } catch (UnknownAccountException e) {
            return Result.fail(CodeMsg.USER_NOT_EXIST);
        } catch (IncorrectCredentialsException e) {
            return Result.fail(CodeMsg.ACCOUNT_OR_PSW_ERR);
        } catch (LockedAccountException e) {
            return Result.fail(CodeMsg.ACCOUNT_LOCKED);
        } catch (AuthenticationException e) {
            return Result.fail(CodeMsg.AUTHOR_ERR);
        }
        return Result.success();
    }

    @RequestMapping(value = "/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success();
    }

}
