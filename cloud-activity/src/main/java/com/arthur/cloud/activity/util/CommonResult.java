package com.arthur.cloud.activity.util;

/**
 * 封装返回数据
 *
 * @author arthur
 * @date
 */
public class CommonResult {

    private boolean hasError = false;

    private String code = "200";
    private String msg = "操作成功";
    private Object data;

    public CommonResult(boolean hasError, String msg, Object data) {
        this.hasError = hasError;
        this.msg = msg;
        this.data = data;
    }

    public CommonResult(boolean hasError, String msg) {
        this.hasError = hasError;
        this.msg = msg;
    }

    public CommonResult(Object data) {
        this.msg = "查询成功";
        this.data = data;
    }

    public CommonResult(boolean hasError) {
        this.hasError = hasError;
        this.msg = "操作失败";
    }

    public CommonResult(String msg) {
        this.hasError = true;
        this.msg = msg;
    }

    public CommonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonResult() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "hasError=" + hasError +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
