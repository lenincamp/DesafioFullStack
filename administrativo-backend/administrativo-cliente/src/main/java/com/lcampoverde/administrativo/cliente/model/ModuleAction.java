package com.lcampoverde.administrativo.cliente.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Permissions for modules.
 */

@Entity
@Table(name = "ACTIONS_MODULES")
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode
public class ModuleAction implements Serializable {

    private static final long serialVersionUID = -5928414619559449921L;
    @EmbeddedId
    @EqualsAndHashCode.Include private ModuleActionId id;

    @Column(name = "URL")
    private String url;

    @ManyToOne
    @JoinColumn(name = "MODULE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Module module;
}
