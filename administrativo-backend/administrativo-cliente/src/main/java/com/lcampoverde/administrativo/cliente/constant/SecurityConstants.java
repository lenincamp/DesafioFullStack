package com.lcampoverde.administrativo.cliente.constant;

public enum SecurityConstants {
    SIGN_UP_URL("/api/auth/signin"),
    TOKEN_TYPE("Bearer");

    private String constant;

    SecurityConstants(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }

}
