package com.lcampoverde.administrativo.cliente.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lcampoverde.administrativo.cliente.model.audit.UserDateAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * @author lenin
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder=true)
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "PROCESS")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Process extends UserDateAudit implements Serializable {

    private static final long serialVersionUID = 3151419920183468954L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @EqualsAndHashCode.Include private Long id;

    @NotBlank
    @Size(max = 60)
    @Column(name = "NAME")
    private String name;

    @NotBlank
    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ENABLED")
    @ColumnDefault("1")
    @NotNull
    @Builder.Default private Boolean enabled = Boolean.TRUE;

    @Column(name = "CATALOG_VALUE_ID")
    @NotNull(message = "Type process is required")
    private Long typeProcess;
    /**
     * type of process
     */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "CATALOG_VALUE_ID", referencedColumnName = "ID", insertable=false ,updatable=false)
    private CatalogValue catalogValue;
    /**
     * Workflow of process
     */
    @OneToMany(mappedBy = "process")
    private Set<WorkFlow> workFlows;
    /**
     * Workflow execution.
     */
    @OneToMany(mappedBy = "process")
    private Set<Execution> executions;

}
