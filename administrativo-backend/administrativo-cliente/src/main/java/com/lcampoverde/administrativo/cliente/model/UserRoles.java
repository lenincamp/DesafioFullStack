package com.lcampoverde.administrativo.cliente.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Permissions for modules.
 */

@Entity
@Table(name = "USER_ROLES")
@Getter
@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserRoles implements Serializable {
    private static final long serialVersionUID = 4365916850976173284L;
    @EmbeddedId
    private UserRolesId id;
}
