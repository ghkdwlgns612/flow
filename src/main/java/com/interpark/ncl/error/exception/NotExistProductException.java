package com.interpark.ncl.error.exception;

import com.interpark.ncl.error.BusinessException;
import com.interpark.ncl.error.ErrorCode;

public class NotExistProductException extends BusinessException {
    public NotExistProductException() {
        super(ErrorCode.NOT_EXITS_PRODUCT);
    }
}
