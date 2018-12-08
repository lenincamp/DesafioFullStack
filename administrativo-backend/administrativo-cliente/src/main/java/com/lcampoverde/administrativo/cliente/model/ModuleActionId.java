package com.lcampoverde.administrativo.cliente.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class ModuleActionId implements Serializable {

    private static final long serialVersionUID = -496015705024009262L;
    @Column(name = "MODULE_ID")
    private Long moduleId;

    @Column(name = "ACTION_ID")
    private Long actionId;
}
