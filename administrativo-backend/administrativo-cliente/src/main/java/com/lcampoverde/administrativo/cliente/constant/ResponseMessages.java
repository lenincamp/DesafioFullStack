package com.lcampoverde.administrativo.cliente.constant;

public enum ResponseMessages {
    SAVE_UPDATE("Successful storage!"),
    DELETE("Successful remove!"),
    NO_FILTERS("There are no search parameters!")
    ;

    private String constant;

    ResponseMessages(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }

}
