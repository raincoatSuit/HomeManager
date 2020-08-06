package com.github.gleans.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthException extends RuntimeException {

    private Integer code;

    public AuthException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

}
