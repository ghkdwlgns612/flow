package com.interpark.ncl.error.exception;

import com.interpark.ncl.error.BusinessException;
import com.interpark.ncl.error.ErrorCode;

public class BlankKewordException extends BusinessException {
    public BlankKewordException() {
        super(ErrorCode.BLANK_KEYWORD);
    }
}
