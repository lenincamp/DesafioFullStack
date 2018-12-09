package com.lcampoverde.administrativo.cliente.model.nopersist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lcampoverde.administrativo.cliente.constant.CatalogKeyWord;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value="Catalog")
public class CatalogVO implements Serializable {

    private static final long serialVersionUID = -5737599102306274315L;

    @Enumerated(EnumType.STRING)
    private CatalogKeyWord keyWord;

    private String name;

    private String description;

    private Boolean enabled;

    private Date endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatalogVO catalogVO = (CatalogVO) o;
        return Objects.equals(keyWord, catalogVO.keyWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyWord);
    }
}
