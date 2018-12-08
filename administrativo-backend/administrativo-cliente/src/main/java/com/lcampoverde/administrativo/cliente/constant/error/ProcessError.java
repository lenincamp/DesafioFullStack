package com.lcampoverde.administrativo.cliente.constant.error;

public enum ProcessError {
    NOT_EXIST("Process does not exist in the system."),
    EXIST("Process name already exists in the system.");

    private String constant;

    ProcessError(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }
}
