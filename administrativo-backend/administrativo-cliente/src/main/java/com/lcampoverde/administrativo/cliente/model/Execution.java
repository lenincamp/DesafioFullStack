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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author lenin
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder=true)
@Entity
@Table(name = "EXECUTION")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(callSuper = false)
public class Execution extends UserDateAudit implements Serializable {
    private static final long serialVersionUID = -4543336424986043894L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @EqualsAndHashCode.Include private Long id;

    @Column(name = "ALIAS")
    @Size(max = 60)
    private String alias;

    @Column(name = "FINISH_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishDate;

    @Column(name = "PROCESS_ID")
    @NotNull
    private Long processId;

    @Column(name = "ENABLED")
    @ColumnDefault("1")
    @NotNull
    @Builder.Default private Boolean enabled = Boolean.TRUE;

    /**
     * Process execution
     */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "PROCESS_ID", referencedColumnName = "ID", insertable=false ,updatable=false)
    private Process process;

    /**
     * Process execution status
     */
    @OneToMany(mappedBy = "execution")
    private Set<Status> statuses;
}
