package com.arthur.cloud.activity.util;

/**
 * 封装返回数据
 *
 * @author yaoyao.zhu
 * @date
 */
public class AjaxResult {

    private int retcode = 1;
    public static final int SUCCESS = 1;
    public static final int FAILED = 0;
    public static final int UNLOGIN = 100;
    private String retmsg = "操作成功";
    private Object data;
    private String url;
    private String title;

    public AjaxResult(int retcode, String retmsg, Object data, String url) {
        this.retcode = retcode;
        this.retmsg = retmsg;
        this.data = data;
        this.url = url;
    }

    public AjaxResult(int retcode, String retmsg, Object data) {
        this.retcode = retcode;
        this.retmsg = retmsg;
        this.data = data;
    }

    public AjaxResult(int retcode, String retmsg) {
        this.retcode = retcode;
        this.retmsg = retmsg;
    }

    public AjaxResult(Object data) {
        this.retmsg = "查询成功";
        this.data = data;
    }

    public AjaxResult(int retcode) {
        this.retcode = retcode;
        this.retmsg = "操作失败";
    }

    public AjaxResult(String retmsg) {
        this.retcode = 0;
        this.retmsg = retmsg;
    }

    public AjaxResult() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "AjaxResult [retcode=" + retcode + ", retmsg=" + retmsg + ", data=" + data + "]";
    }

}
