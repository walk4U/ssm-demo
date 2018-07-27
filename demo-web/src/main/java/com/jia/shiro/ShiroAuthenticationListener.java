package com.jia.shiro;

import com.jia.model.entity.UserDO;
import com.jia.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @Auther: jia
 * @Date: 2018/7/27 13:50
 * @Description:
 */
public class ShiroAuthenticationListener implements AuthenticationListener {

    @Resource
    ShiroUserRealm shiroUserRealm;

    @Override
    public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {
        UserDO user = shiroUserRealm.loadUserInfoFromDb((String)token.getPrincipal());
        if (user != null) {
            ShiroUtil.setValueToSession("userInfoContext", user);
        }
    }

    @Override
    public void onFailure(AuthenticationToken token, AuthenticationException ae) {

    }

    @Override
    public void onLogout(PrincipalCollection principals) {

    }
}
