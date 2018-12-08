package com.lcampoverde.administrativo.cliente.constant.error;

public enum LoggerError {
    SAVE("error when saving: "),
    UPDATE("error when updating: "),
    DELETE("error when removing: ")
    ;

    private String constant;

    LoggerError(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }

}
