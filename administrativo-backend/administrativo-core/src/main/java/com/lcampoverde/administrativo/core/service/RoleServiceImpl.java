package com.lcampoverde.administrativo.core.service;

import com.lcampoverde.administrativo.cliente.gestor.RoleGestor;
import com.lcampoverde.administrativo.cliente.model.Role;
import com.lcampoverde.administrativo.cliente.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author lenin
 */
@Service
@Lazy
public class RoleServiceImpl implements RoleService {
    @Autowired
    @Lazy
    private RoleGestor roleGestor;

    /**
     * {@inheritDoc}
     */
    @Override
    public Role findByName(String role) {
        return roleGestor.findByName(role);
    }
}
