package com.lcampoverde.administrativo.cliente.model.nopersist;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value="Status", parent = ExecutionVO.class)
public class StatusVO implements Serializable {
    private static final long serialVersionUID = -5020997724144606744L;

    private Long id;

    private Date finishDate;

    @ApiModelProperty(value = "Value id of status", allowableValues = "INI, EP, EEA, AUT, ED, FIN, REC")
    private Long catalogValueId;

    @ApiModelProperty(value = "catalogValue")
    private CatalogValueVO catalogValueVO;

    /**
     * Actors on status.
     */
    @ApiModelProperty(value = "user")
    private Set<SignUpRequest> users = new HashSet<>();

    /**
     * Conclusions of status.
     */
    @ApiModelProperty(value = "Conclusions")
    private Set<ConclusionsVO> conclusions;

    /**
     * Observations of status.
     */
    @ApiModelProperty(value = "Observations")
    private Set<ObservationsVO> observations;

    /**
     * Objectives of status.
     */
    @ApiModelProperty(value = "Objectives")
    private Set<ObjectivesVO> objectives;

    /**
     * Process execution
     */
    private Long executionId;
}
