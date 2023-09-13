package com.njts.exception;

public class BusinessException extends  RuntimeException{
    //构建空参有参构造函数
    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }
}
