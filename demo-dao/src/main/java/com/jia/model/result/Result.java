package com.jia.model.result;

import java.io.Serializable;

/**
 * @Auther: jia
 * @Date: 2018/7/18 16:23
 * @Description: 返回结果集封装类
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 4102259965593277454L;

    private int code;

    private String message;

    private T data;

    /**
     * 构造函数返回成功
     */
    private Result(T data) {
        this.code = 0;
        this.message = "success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg) {
        if(codeMsg == null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.message = codeMsg.getMessage();
    }

    /**
     * 成功返回带参数
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return  new Result<>(data);
    }

    /**
     * 成功返回不带参数
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> success() {
        return (Result<T>) success("");
    }

    /**
     * 失败时返回
     * @param codeMsg
     * @param <T>
     * @return
     */
    public static <T> Result<T> fail(CodeMsg codeMsg) {
        return new Result<>(codeMsg);
    }

    /**
     * 失败时候的调用,扩展消息参数
     * @param codeMsg
     * @param msg
     * @return
     */
    public static <T> Result<T> error(CodeMsg codeMsg, String msg) {
        codeMsg.setMessage(codeMsg.getMessage() + "--" + msg);
        return new Result<T>(codeMsg);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}
