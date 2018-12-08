package com.lcampoverde.administrativo.core.gestor;

import com.lcampoverde.administrativo.cliente.gestor.RoleGestor;
import com.lcampoverde.administrativo.cliente.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author lenin
 */
@Component
@Lazy
public class RoleGestorImpl implements RoleGestor {
    @Autowired
    @Lazy
    private RoleRepository roleRepository;
    @Override
    public RoleRepository getRoleRepository() {
        return roleRepository;
    }
}
