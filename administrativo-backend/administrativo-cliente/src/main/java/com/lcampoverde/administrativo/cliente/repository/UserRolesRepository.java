package com.lcampoverde.administrativo.cliente.repository;

import com.lcampoverde.administrativo.cliente.model.UserRoles;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Lazy
public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
    Optional<UserRoles> findById_UserIdAndId_RoleId(Long userId, Long roleId);
}
