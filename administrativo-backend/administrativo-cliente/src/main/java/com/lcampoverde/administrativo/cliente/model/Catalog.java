package com.lcampoverde.administrativo.cliente.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lcampoverde.administrativo.cliente.constant.CatalogKeyWord;
import com.lcampoverde.administrativo.cliente.model.audit.UserDateAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
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
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CATALOG")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Catalog extends UserDateAudit implements Serializable {
    private static final long serialVersionUID = -4202147258794998898L;
    @Id
    @Column(name = "KEYWORD", length = 4)
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include private CatalogKeyWord keyWord;

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

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @OneToMany(mappedBy = "catalog")
    private Set<CatalogValue> catalogValues;
}
