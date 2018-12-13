package com.lcampoverde.administrativo.cliente.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lenin
 * Modules of system.
 */
@Entity
@Table(name = "MODULE")
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(callSuper = false)
public class Module extends Asserts implements Serializable {

    private static final long serialVersionUID = -4924058894440960413L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @EqualsAndHashCode.Include private Long id;

    @Column(name = "URL")
    @URL
    @NotBlank
    private String url;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ACTIONS_MODULES",
            joinColumns = @JoinColumn(name = "MODULE_ID"),
            inverseJoinColumns = @JoinColumn(name = "ACTION_ID"))
    private Set<Actions> actionsSet = new HashSet<>();

}
