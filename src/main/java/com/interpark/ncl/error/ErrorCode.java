package com.interpark.ncl.error;

public enum ErrorCode {
    NOT_FOUND_USER(4040, "존재하지 않는 유저입니다. 다시 확인 부탁드립니다."),
    BLANK_KEYWORD(4040, "키워드가 입력되지 않았습니다. 다시 입력 부탁드립니다."),
    NOT_EXITS_PRODUCT(5030,"상품 데이터가 존재하지 않습니다. 잠시 후 다시 시도부탁드립니다.");

    private final int code;
    private final String message;

    ErrorCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return code;
    }
}