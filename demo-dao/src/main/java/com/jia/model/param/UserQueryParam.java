package com.jia.model.param;

import com.jia.model.page.PageQuery;

/**
 * @Auther: jia
 * @Date: 2018/7/25 11:14
 * @Description:
 */
public class UserQueryParam extends PageQuery {

    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
