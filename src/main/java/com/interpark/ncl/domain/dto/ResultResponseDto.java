package com.interpark.ncl.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponseDto<T> {
    private Integer code;
    private String message;
    private T data;
}