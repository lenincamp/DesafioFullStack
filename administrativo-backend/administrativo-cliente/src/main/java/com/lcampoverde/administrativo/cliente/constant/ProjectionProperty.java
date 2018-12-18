package com.lcampoverde.administrativo.cliente.constant;

public enum ProjectionProperty {
    PROJECTION_PROPERTY_PROCESS("process"),
    PROJECTION_PROPERTY_STATUS("status"),
    PROJECTION_PROPERTY_EXECUTION("execution"),
    PROJECTION_PROPERTY_CATALOG_VALUE("catalogValue"),
    PROJECTION_PROPERTY_USER("user"),
    PROJECTION_PROPERTY_OBSERVATION("observations"),
    PROJECTION_PROPERTY_ENABLED("enabled"),
    PROJECTION_PROPERTY_ID("id"),
    ;

    private String constant;

    ProjectionProperty(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }

}
