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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author lenin
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder=true)
@Entity
@Table(name = "OBSERVATIONS")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Observations extends UserDateAudit implements Serializable {

    private static final long serialVersionUID = 2157290304093990974L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRIPTION")
    @Size(max = 150)
    private String description;

    @NotNull
    @Column(name = "STATUS_ID")
    private Long statusId;

    @NotNull
    @Column(name = "CATALOG_VALUE_ID")
    private Long catalogValueId;

    @Column(name = "ENABLED")
    @ColumnDefault("1")
    @NotNull
    @Builder.Default private Boolean enabled = Boolean.TRUE;

    /**
     * Severity.(mild, medium, high, very high, extremely high)
     */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "CATALOG_VALUE_ID", referencedColumnName = "ID", insertable=false ,updatable=false)
    private CatalogValue catalogValue;

    /**
     * status of process executions.
     */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID", referencedColumnName = "ID", insertable=false ,updatable=false)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Observations that = (Observations) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
