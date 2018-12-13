package com.lcampoverde.administrativo.cliente.model.nopersist;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author lenin
 * Actions on system (can-read,can-update,can-insert,can-remove)
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ActionsVO implements Serializable {

    private static final long serialVersionUID = 7928915803599375747L;
    @EqualsAndHashCode.Include private Long id;

    @Column(name = "DESCRIPTION")
    @NotBlank
    @Size(max = 100)
    private String description;

    @Column(name = "TITLE")
    @NotBlank
    @Size(max = 32)
    private String title;

    @URL
    private String url;
}
