package com.lcampoverde.administrativo.cliente.constant.error;

public enum CatalogError {
    NOT_EXIST("Catalog does not exist in the system.");

    private String constant;

    CatalogError(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }
}
