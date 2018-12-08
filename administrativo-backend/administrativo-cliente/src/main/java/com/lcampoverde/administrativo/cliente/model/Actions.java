package com.lcampoverde.administrativo.cliente.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author lenin
 * Actions on system (can-read,can-update,can-insert,can-remove)
 */
@Entity
@Table(name = "ACTION")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Actions extends Asserts implements Serializable {

    private static final long serialVersionUID = -2974492718367517933L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Actions actions = (Actions) o;
        return Objects.equals(id, actions.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
