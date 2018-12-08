package com.lcampoverde.administrativo.cliente.service;

import com.lcampoverde.administrativo.cliente.model.Role;

/**
 * @author lenin
 */
public interface RoleService {
    /**
     * Find role by name.
     * @param role role of user(ROLE_ADMIN, ROLE_USER, ROLE_USER_FINAL).
     * @return
     */
    Role findByName(String role);
}
