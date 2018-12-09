package com.lcampoverde.administrativo.cliente.model.nopersist;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value="Execution",  parent = ProcessVO.class)
public class ExecutionVO implements Serializable {
    private static final long serialVersionUID = 7298055225577998087L;

    private Long id;

    private String alias;

    private Date finishDate;

    @NotBlank
    private Long processId;

    @ApiModelProperty(value = "statuses")
    private Set<StatusVO> statuses;

    private Date createAt;

    public ExecutionVO(Long id, String alias, Date finishDate, Long processId) {
        this.id = id;
        this.alias = alias;
        this.finishDate = finishDate;
        this.processId = processId;
    }
}
