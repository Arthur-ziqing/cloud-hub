package com.arthur.cloud.activity.exception;

/**
 * @Author 秦梓青
 * @ClassName
 * @Description 业务异常信息
 * @create 2020-06-22 17:02
 * @Version 1.0
 **/
public class BusinessException extends Exception{

    private static final long serialVersionUID = -6915489250575047819L;

    private static final String INNER_ERROR = "0000";

    private final String errorCode;

    private String messageKey;

    private Object[] args;

    public BusinessException(String messageKey) {
        super(messageKey);
        this.messageKey = messageKey;
        this.errorCode = INNER_ERROR;
    }

    public BusinessException(String messageKey, Throwable cause) {
        super(messageKey, cause);
        this.messageKey = messageKey;
        this.errorCode = INNER_ERROR;
    }

    public BusinessException(String messageKey, Object... args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.errorCode = INNER_ERROR;
        this.args = args;
    }

    public BusinessException(String messageKey,String errorCode) {
        super(messageKey);
        this.messageKey = messageKey;
        this.errorCode = errorCode;
    }


    public BusinessException(String messageKey,String errorCode, Object... args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.errorCode = errorCode;
        this.args = args;
    }


    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
