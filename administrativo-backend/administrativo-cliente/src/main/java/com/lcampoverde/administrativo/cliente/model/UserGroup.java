package com.lcampoverde.administrativo.cliente.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lcampoverde.administrativo.cliente.constant.UserGroupName;
import com.lcampoverde.administrativo.cliente.model.audit.UserDateAudit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lenin
 * Class for assigment permission for groups users.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USER_GROUP")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(callSuper = false)
public class UserGroup extends UserDateAudit implements Serializable {

    private static final long serialVersionUID = 1446243722130725078L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @EqualsAndHashCode.Include private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "NAME", length = 60)
    private UserGroupName name;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ROLE_GROUP",
            joinColumns = @JoinColumn(name = "GROUP_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERGROUPID", referencedColumnName = "ID")
    private UserGroup groupParent;

    @OneToMany(mappedBy = "groupParent")
    private Set<UserGroup> groupChildren;

}
