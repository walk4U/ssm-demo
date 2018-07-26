package com.jia.model.vo;

import com.jia.model.entity.UserDO;

/**
 * @Auther: jia
 * @Date: 2018/7/24 15:28
 * @Description:
 */
public class UserVO {

    public String name;

    public int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /**
     * UserVO 转换
     * @param userDO
     * @return
     */
    public static UserVO convertToVo(UserDO userDO) {
        if(userDO == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setName(userDO.getName());
        userVO.setAge(userDO.getAge());
        return userVO;
    }
}
