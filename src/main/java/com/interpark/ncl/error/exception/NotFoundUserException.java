package com.interpark.ncl.error.exception;


import com.interpark.ncl.error.BusinessException;
import com.interpark.ncl.error.ErrorCode;

public class NotFoundUserException extends BusinessException {

    public NotFoundUserException() {super(ErrorCode.NOT_FOUND_USER);}
}
