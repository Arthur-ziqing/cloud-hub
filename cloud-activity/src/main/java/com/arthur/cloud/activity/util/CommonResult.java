package com.arthur.cloud.activity.util;

/**
 * 封装返回数据
 *
 * @author arthur
 * @date
 */
public class CommonResult {

    private int code = 1;
    private boolean hasError;

    private String msg = "操作成功";
    private Object data;

    public CommonResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CommonResult(boolean hasError, String msg, Object data) {
        this.hasError = hasError;
        this.msg = msg;
        this.data = data;
    }

    public CommonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonResult(Object data) {
        this.msg = "查询成功";
        this.data = data;
    }

    public CommonResult(int code) {
        this.code = code;
        this.msg = "操作失败";
    }

    public CommonResult(String msg) {
        this.code = 0;
        this.msg = msg;
    }

    public CommonResult() {
    }

    public int getRetcode() {
        return code;
    }

    public void setRetcode(int code) {
        this.code = code;
    }

    public String getRetmsg() {
        return msg;
    }

    public void setRetmsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "code=" + code +
                ", hasError=" + hasError +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
