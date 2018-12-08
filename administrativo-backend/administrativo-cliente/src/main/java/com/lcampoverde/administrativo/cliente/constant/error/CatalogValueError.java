package com.lcampoverde.administrativo.cliente.constant.error;

public enum CatalogValueError {
    NOT_EXIST("Catalog value does not exist in the system."),
    EXIST_BY_KEYWORD("Catalog value keyword exist in the system.");

    private String constant;

    CatalogValueError(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }
}
