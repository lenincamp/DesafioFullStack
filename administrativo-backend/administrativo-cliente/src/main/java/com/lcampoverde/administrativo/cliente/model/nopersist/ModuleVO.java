package com.lcampoverde.administrativo.cliente.model.nopersist;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lenin
 * Modules pojo.
 */
@Getter
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode
public class ModuleVO implements Serializable {

    private static final long serialVersionUID = 5783441517529287132L;
    @EqualsAndHashCode.Include private Long id;

    @URL
    private String url;

    @Builder.Default private Set<ActionsVO> actions = new HashSet<>();

    @Size(max = 100)
    private String description;

    @Column(name = "TITLE")
    @NotBlank
    @Size(max = 32)
    private String title;
}
