package com.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse implements Serializable {
    private int code;
    private String message;
    private boolean result;

    private ServerResponse(int code){
        this.code = code;
    }
    private ServerResponse(int code, boolean result){
        this.code = code;
        this.result = result;
    }

    private ServerResponse(int code, String message, boolean result){
        this.code = code;
        this.message = message;
        this.result = result;
    }

    private ServerResponse(int code, String message){
        this.code = code;
        this.message = message;
    }

    private boolean isSuccess(){
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    public int getCode(){
        return code;
    }
    public boolean getResult(){
        return result;
    }
    public String getmessage(){
        return message;
    }


    public static  ServerResponse createBySuccess(){
       return new ServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc(), Const.regular);
    }

    public static  ServerResponse createByErrorMessage(String errorMessage){
        return new ServerResponse(ResponseCode.ERROR.getCode(),errorMessage, Const.irregular);
    }

    public static  ServerResponse createByError(){
        return new ServerResponse(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc(), Const.irregular);
    }
}
