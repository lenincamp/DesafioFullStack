package com.lcampoverde.administrativo.cliente.repository;

import com.lcampoverde.administrativo.cliente.constant.RoleName;
import com.lcampoverde.administrativo.cliente.model.Role;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Lazy
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
