package com.jia.controller;

import com.google.common.collect.Lists;
import com.jia.model.entity.UserDO;
import com.jia.model.param.UserQueryParam;
import com.jia.model.result.CodeMsg;
import com.jia.model.result.Result;
import com.jia.model.vo.UserListVO;
import com.jia.model.vo.UserVO;
import com.jia.redis.RedisService;
import com.jia.service.UserService;
import com.jia.service.password.PasswordEncrypt;
import com.jia.shiro.ShiroUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: jia
 * @Date: 2018/7/27 13:50
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    PasswordEncrypt passwordEncrypt;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getAllUser() {
        logger.info("Start to get all user");
        Object allUser = redisService.get("allUser");
        List<UserDO> userDOS;
        if(allUser == null) {
            userDOS = userService.findAllUser();
            redisService.set("allUser", userDOS, 3600 * 2L);
            return Result.success(userDOS);
        } else {
            return  Result.success(allUser);
        }
    }

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
    public Result register(@RequestBody UserDO userDO) {
        if(userDO == null || StringUtils.isBlank(userDO.getAccount())
                || StringUtils.isBlank(userDO.getPassword())
                || StringUtils.isBlank(userDO.getName())) {
            return Result.fail(CodeMsg.PARAMETER_ISNULL);
        }
        passwordEncrypt.encryptPassword(userDO);
        int i = userService.insert(userDO);
        if(i==1) {
            return Result.success();
        } else {
            return Result.fail(CodeMsg.USER_REGISTER_FAIL);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private Result login(@RequestBody UserDO user) {
        if(user ==null || StringUtils.isBlank(user.getAccount()) || StringUtils.isBlank(user.getPassword())) {
            return Result.fail(CodeMsg.PARAMETER_ISNULL);
        }
        // 创建用户登录信息
        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword());
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

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public Result getUserInfo() {
        UserDO userDO = ShiroUtil.getUser();
        UserVO userVO = UserVO.convertToVo(userDO);
        return Result.success(userVO);
    }

}
