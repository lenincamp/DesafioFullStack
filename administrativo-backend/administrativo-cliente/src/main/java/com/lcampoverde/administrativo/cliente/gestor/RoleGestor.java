package com.lcampoverde.administrativo.cliente.gestor;

import com.lcampoverde.administrativo.cliente.constant.RoleName;
import com.lcampoverde.administrativo.cliente.exception.AppException;
import com.lcampoverde.administrativo.cliente.model.Role;
import com.lcampoverde.administrativo.cliente.repository.RoleRepository;

/**
 * @author lenin
 */
public interface RoleGestor {
    /**
     * Get repository role.
     * @return repository roles.
     */
    RoleRepository getRoleRepository();

    /**
     * Find role by name.
     * @param role role of user(ROLE_ADMIN, ROLE_USER, ROLE_USER_FINAL).
     * @return
     */
    default Role findByName(String role) {
        return getRoleRepository().findByName(RoleName.valueOf(role))
                .orElseThrow(() -> new AppException("User Role not set."));
    }
}
