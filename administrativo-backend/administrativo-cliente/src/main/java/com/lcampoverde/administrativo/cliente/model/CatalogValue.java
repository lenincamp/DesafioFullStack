package com.lcampoverde.administrativo.cliente.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lcampoverde.administrativo.cliente.constant.CatalogKeyWord;
import com.lcampoverde.administrativo.cliente.constant.CatalogValueKeyWord;
import com.lcampoverde.administrativo.cliente.model.audit.UserDateAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
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
@Table(name = "CATALOG_VALUE", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
            "KEYWORD"
    })
})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CatalogValue extends UserDateAudit implements Serializable {

    private static final long serialVersionUID = 211456349234199935L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 60)
    @Column(name = "NAME")
    private String name;

    @NotBlank
    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "KEYWORD", length = 4)
    @Enumerated(EnumType.STRING)
    @NaturalId
    @NotBlank
    private CatalogValueKeyWord keyWord;

    @Column(name = "ENABLED")
    @ColumnDefault("1")
    @Builder.Default private Boolean enabled = Boolean.TRUE;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "CATALOG_ID", length = 4,insertable=false ,updatable=false)
    @Enumerated(EnumType.STRING)
    @NotBlank
    private CatalogKeyWord catalogId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "CATALOG_ID", referencedColumnName = "KEYWORD")
    private Catalog catalog;
}
