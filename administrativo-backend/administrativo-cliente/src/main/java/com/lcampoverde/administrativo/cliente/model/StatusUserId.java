package com.lcampoverde.administrativo.cliente.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter
public class StatusUserId implements Serializable {

    private static final long serialVersionUID = -4339898743997936143L;
    @Column(name = "STATUS_ID")
    private Long statusId;

    @Column(name = "USER_ID")
    private Long userId;
}
