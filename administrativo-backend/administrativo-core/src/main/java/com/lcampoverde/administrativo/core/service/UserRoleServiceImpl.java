package com.lcampoverde.administrativo.core.service;

import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.gestor.UserRoleGestor;
import com.lcampoverde.administrativo.cliente.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lenin
 */
@Service
@Lazy
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    @Lazy
    private UserRoleGestor userRoleGestor;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public void saveUserRole(Long userId, Long roleId) {
        userRoleGestor.saveUserRole(userId, roleId);
    }
}
