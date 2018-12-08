package com.lcampoverde.administrativo.cliente.constant.error;

public enum CrudError {
    SAVE("An error occurred while saving, please contact the administrator"),
    UPDATE("An error occurred while updating, please contact the administrator"),
    DELETE("An error occurred while deleting, please contact the administrator")
    ;

    private String constant;

    CrudError(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }

}



