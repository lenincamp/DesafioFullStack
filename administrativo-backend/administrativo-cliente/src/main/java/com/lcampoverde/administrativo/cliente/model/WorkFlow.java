package com.lcampoverde.administrativo.cliente.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lenin
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder=true)
@Entity
@Table(name = "WORKFLOW")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode
public class WorkFlow implements Serializable {
    private static final long serialVersionUID = -1727254825979380805L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @EqualsAndHashCode.Include private Long id;

    @NotBlank
    @Size(max = 60)
    @Column(name = "NAME")
    private String name;

    @NotBlank
    @Size(max = 250)
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "NUMBER_OF_STEP")
    private Integer numberStep;

    @Column(name = "TIME_DURATION_ESTIMATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeDurationEstimated;

    @Column(name = "NUMBER_RESOURCE")
    private Integer numberOfResource;

    @NotNull
    @Column(name = "PROCESS_ID", insertable=false ,updatable=false)
    private Long processId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "PROCESS_ID", referencedColumnName = "ID")
    private Process process;

}
