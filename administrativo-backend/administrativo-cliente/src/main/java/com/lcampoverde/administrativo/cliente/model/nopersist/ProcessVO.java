package com.lcampoverde.administrativo.cliente.model.nopersist;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author lenin
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value="Process", description = "Process", subTypes = {WorkFlowVO.class, ExecutionVO.class})
public class ProcessVO implements Serializable {

    private static final long serialVersionUID = -3045936485281478412L;
    private Long id;

    private String name;

    private String description;

    private Boolean enabled;

    private Long typeProcess;

    private Set<WorkFlowVO> workFlows;

    private Set<ExecutionVO> executions;

    private CatalogValueVO catalogValueVO;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessVO processVO = (ProcessVO) o;
        return Objects.equals(id, processVO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
