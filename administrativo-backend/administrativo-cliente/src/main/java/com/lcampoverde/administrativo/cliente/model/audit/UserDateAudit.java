package com.lcampoverde.administrativo.cliente.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author lenin
 * Class for auditing
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt", "createdBy", "updatedBy"},
        allowGetters = true
)
@Getter
@Setter
public abstract class UserDateAudit {

    @JsonIgnore
    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;

    @JsonIgnore
    @LastModifiedDate
    @Column(name = "UPDATE_AT")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedAt;

    @JsonIgnore
    @CreatedBy
    @Column(name = "CREATED_BY", updatable = false)
    protected Long createdBy;

    @JsonIgnore
    @LastModifiedBy
    @Column(name = "UPDATE_BY")
    protected Long updatedBy;
}
