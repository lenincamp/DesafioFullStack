package com.lcampoverde.administrativo.cliente.model.nopersist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lcampoverde.administrativo.cliente.constant.CatalogKeyWord;
import com.lcampoverde.administrativo.cliente.constant.CatalogValueKeyWord;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value="CatalogValue",  parent = CatalogVO.class)
public class CatalogValueVO implements Serializable {

    private static final long serialVersionUID = 3887765407561235860L;
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private CatalogValueKeyWord keyWord;

    private Boolean enabled;

    private Date endDate;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private CatalogKeyWord catalogId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatalogValueVO that = (CatalogValueVO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
