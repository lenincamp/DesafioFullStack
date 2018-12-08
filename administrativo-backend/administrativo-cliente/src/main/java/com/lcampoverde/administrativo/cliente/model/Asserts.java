package com.lcampoverde.administrativo.cliente.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lcampoverde.administrativo.cliente.model.audit.UserDateAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class Asserts extends UserDateAudit {

    @Column(name = "DESCRIPTION")
    @NotBlank
    @Size(max = 100)
    private String description;

    @Column(name = "TITLE")
    @NotBlank
    @Size(max = 32)
    private String title;
}
