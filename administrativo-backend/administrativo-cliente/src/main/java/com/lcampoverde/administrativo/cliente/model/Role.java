package com.lcampoverde.administrativo.cliente.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lcampoverde.administrativo.cliente.constant.RoleName;
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
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lenin
 * Roles for users.
 */
@Entity
@Table(name = "ROLE")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Role implements Serializable {

    private static final long serialVersionUID = 3745224488956847677L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @EqualsAndHashCode.Include private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "NAME", length = 60)
    private RoleName name;


    @Column(name = "DESCRIPTION", length = 100)
    private String description;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ROLE_PERMISSION",
            joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = { @JoinColumn(name = "MODULE_ID",referencedColumnName = "MODULE_ID"), @JoinColumn(name = "ACTION_ID", referencedColumnName = "ACTION_ID")},
            inverseForeignKey=@ForeignKey(name="permissions_fk")
    )
    private Set<ModuleAction> permissions = new HashSet<>();

    public Role(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.permissions = role.getPermissions();
    }
}
