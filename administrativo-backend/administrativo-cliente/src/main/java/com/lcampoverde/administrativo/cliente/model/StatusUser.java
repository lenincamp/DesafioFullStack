package com.lcampoverde.administrativo.cliente.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * Actors for status
 */

@Entity
@Table(name = "STATUS_USER")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode
public class StatusUser implements Serializable {

    private static final long serialVersionUID = -1674333475875815574L;
    @EmbeddedId
    @EqualsAndHashCode.Include private StatusUserId id;

    /**
     * status of process executions.
     */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID", referencedColumnName = "ID", insertable=false ,updatable=false)
    private Status status;

    /**
     * status of process executions.
     */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", insertable=false ,updatable=false)
    private User user;

    /**
     * Observations by user of status.
     */
    @OneToMany
    @JoinColumn(name="STATUS_ID", referencedColumnName = "STATUS_ID", insertable = false, updatable = false)
    @JoinColumn(name="USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    private Set<Observations> observations;
}
