package com.virus.pt.common.exception;


import com.virus.pt.common.enums.ResultEnum;

public class TipException extends Exception {
    private ResultEnum resultEnum;

    public TipException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.resultEnum = resultEnum;
    }

    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }
}
