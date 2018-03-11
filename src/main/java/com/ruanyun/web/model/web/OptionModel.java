package com.ruanyun.web.model.web;

public class OptionModel {
    private String msgTime;
    private String reqSource;
    private String token;
    private String bizCode;
    private Object content;

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getReqSource() {
        return reqSource;
    }

    public void setReqSource(String reqSource) {
        this.reqSource = reqSource;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }


}
