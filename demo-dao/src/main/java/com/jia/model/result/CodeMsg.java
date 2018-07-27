package com.jia.model.result;

/**
 * @Auther: jia
 * @Date: 2018/7/18 16:02
 * @Description: 返回码
 */
public class CodeMsg {

    private int code;

    private String message;

    //成功返回
    public static CodeMsg SUCCESS = new CodeMsg(0,"success");

    // 通用异常
    public static CodeMsg SERVER_EXCEPTION = new CodeMsg(500100,"服务端异常");
    public static CodeMsg PARAMETER_ISNULL = new CodeMsg(500101,"输入参数为空");

    // 业务异常
    public static CodeMsg USER_NOT_EXIST = new CodeMsg(500102,"用户不存在");
    public static CodeMsg USER_REGISTER_FAIL = new CodeMsg(500105,"用户注册失败");
    public static CodeMsg ACCOUNT_OR_PSW_ERR = new CodeMsg(500106,"用户名或密码错误");
    public static CodeMsg ACCOUNT_LOCKED = new CodeMsg(500107,"账号被锁定");
    public static CodeMsg AUTHOR_ERR = new CodeMsg(500108,"登录失败");
    public static CodeMsg NO_LOGIN = new CodeMsg(500109,"未登录");

    public CodeMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
