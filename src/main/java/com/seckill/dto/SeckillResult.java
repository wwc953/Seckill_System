package com.seckill.dto;

/**
 * 所有ajax请求返回类型，封装json结果
 * <p>
 * Created by Roll on 2017/10/19.
 */
public class SeckillResult<T> {

    private boolean success;//是否成功

    private T data;//json数据

    private String error;//错误信息

    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }




}
