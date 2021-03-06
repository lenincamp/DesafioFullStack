package com.lcampoverde.administrativo.core.service;

import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.gestor.UserGestor;
import com.lcampoverde.administrativo.cliente.model.Role;
import com.lcampoverde.administrativo.cliente.model.User;
import com.lcampoverde.administrativo.cliente.model.nopersist.SignUpRequest;
import com.lcampoverde.administrativo.cliente.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

/**
 * @author lenin
 */
@Service
@Lazy
public class UserServiceImpl implements UserService {
    @Autowired
    @Lazy
    private UserGestor userGestor;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public void createUser(SignUpRequest user, Role role, Long userId) {
        userGestor.createUser(user, role, userId);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Long getUserIdByUserName(String userName) {
        return userGestor.getUserIdByUserName(userName);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean existsByUserName(String userName) {
        return userGestor.existsByUserName(userName);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean existsByEmail(String userEmail) {
        return userGestor.existsByEmail(userEmail);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public void saveUser(User user) {
        userGestor.saveUser(user);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findUserById(Long userId) {
       return  userGestor.findById(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public void deleteUserById(Long userId) {
        userGestor.deleteUserById(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<SignUpRequest> findByEnabledAndRole(Boolean enabled, Long roleId) {
        return userGestor.findByEnabledAndRole(enabled, roleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<SignUpRequest> findByEnabled(Boolean enabled) {
        return userGestor.findByEnabled(enabled);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public SignUpRequest updateUser(Long userId, Long roleId, SignUpRequest user) {
        return userGestor.updateUser(userId, roleId, user);
    }
}
