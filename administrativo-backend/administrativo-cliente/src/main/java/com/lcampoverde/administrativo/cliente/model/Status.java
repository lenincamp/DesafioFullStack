package com.lcampoverde.administrativo.cliente.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lcampoverde.administrativo.cliente.model.audit.UserDateAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author lenin
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder=true)
@Entity
@Table(name = "STATUS")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Status extends UserDateAudit implements Serializable {

    private static final long serialVersionUID = -2349143036636034963L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FINISH_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishDate;

    @NotNull
    @Column(name = "CATALOG_VALUE_ID")
    private Long catalogValueId;

    @NotNull
    @Column(name = "EXECUTION_ID")
    private Long executionId;

    @Column(name = "ENABLED")
    @ColumnDefault("1")
    @NotNull
    @Builder.Default private Boolean enabled = Boolean.TRUE;

    /**
     * Value status.(INI, EP, EEA, AUT, ED, FIN, REC)
     */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "CATALOG_VALUE_ID", referencedColumnName = "ID", insertable=false ,updatable=false)
    private CatalogValue catalogValue;

    /**
     * Conclusions of status.
     */
    @OneToMany(mappedBy = "status")
    private Set<StatusUser> statusUsers;


    /**
     * Conclusions of status.
     */
    @OneToMany(mappedBy = "status")
    private Set<Conclusions> conclusions;

    /**
     * Objectives of status.
     */
    @OneToMany(mappedBy = "status")
    private Set<Objectives> objectives;

    /**
     * Process execution
     */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "EXECUTION_ID", referencedColumnName = "ID", insertable=false ,updatable=false)
    private Execution execution;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(id, status.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

