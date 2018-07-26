package com.jia.model.vo;

import java.util.List;

/**
 * @Auther: jia
 * @Date: 2018/7/25 12:20
 * @Description:
 */
public class UserListVO {

    private int total;

    private List<UserVO> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<UserVO> getList() {
        return list;
    }

    public void setList(List<UserVO> list) {
        this.list = list;
    }
}
