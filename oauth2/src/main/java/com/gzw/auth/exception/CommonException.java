package com.gzw.auth.exception;

import com.gzw.auth.enums.ResultEnum;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by qzj on 2018/2/25
 */
public class CommonException extends AuthenticationException {

    private Integer code;
    private ResultEnum resultEnum;
    public CommonException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
        this.resultEnum = resultEnum;
    }

    public CommonException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }
}
