package com.lcampoverde.administrativo.core.gestor;

import com.lcampoverde.administrativo.cliente.gestor.UserRoleGestor;
import com.lcampoverde.administrativo.cliente.repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author lenin
 */
@Component
@Lazy
public class UserRoleGestorImpl implements UserRoleGestor {
    @Autowired
    @Lazy
    private UserRolesRepository userRolesRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRolesRepository getUserRolesRepository() {
        return userRolesRepository;
    }
}
