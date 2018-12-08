package com.lcampoverde.administrativo.cliente.constant.error;

public enum ExecutionError {
    NOT_EXIST("Execution does not exist in the system.");
    private String constant;

    ExecutionError(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }
}
