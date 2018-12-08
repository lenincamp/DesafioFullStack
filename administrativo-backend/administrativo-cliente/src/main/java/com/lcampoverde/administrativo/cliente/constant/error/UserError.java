package com.lcampoverde.administrativo.cliente.constant.error;

public enum UserError {
    NOT_EXIST("User does not exist in the system."),
    USER_NAME("Username is already taken!"),
    EMAIL("Email Address already in use!"),
    ;

    private String constant;

    UserError(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }
}
