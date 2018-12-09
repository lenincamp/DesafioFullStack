package com.lcampoverde.administrativo.cliente.model.nopersist;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value="Observations", parent = StatusVO.class)
public class ObservationsVO implements Serializable {
    private static final long serialVersionUID = 8084240724349649027L;

    private Long id;

    private String description;

    private Long statusId;
    /**
     * Severity.(mild, medium, high, very high, extremely high)
     */
    private Long catalogValueId;

    private Long userId;

    CatalogValueVO catalogValueVO;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObservationsVO that = (ObservationsVO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
