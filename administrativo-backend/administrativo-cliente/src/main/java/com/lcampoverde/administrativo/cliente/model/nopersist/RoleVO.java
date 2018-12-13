package com.lcampoverde.administrativo.cliente.model.nopersist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lcampoverde.administrativo.cliente.constant.RoleName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lenin
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value="Role", description = "Role")
public class RoleVO implements Serializable {

    private static final long serialVersionUID = -6478398426293719903L;

    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    private RoleName name;

    private String description;

    @Builder.Default private Set<ModuleVO> modules = new HashSet<>();
}