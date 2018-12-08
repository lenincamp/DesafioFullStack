package com.lcampoverde.administrativo.cliente.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class UserRolesId implements Serializable {

    private static final long serialVersionUID = -1911217381249113243L;
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ROLE_ID")
    private Long roleId;
}
