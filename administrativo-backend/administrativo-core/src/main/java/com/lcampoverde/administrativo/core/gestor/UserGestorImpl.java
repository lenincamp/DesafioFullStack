package com.lcampoverde.administrativo.core.gestor;

import com.lcampoverde.administrativo.cliente.constant.error.CrudError;
import com.lcampoverde.administrativo.cliente.constant.error.LoggerError;
import com.lcampoverde.administrativo.cliente.constant.error.UserError;
import com.lcampoverde.administrativo.cliente.exception.AppException;
import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.gestor.UserGestor;
import com.lcampoverde.administrativo.cliente.model.Role;
import com.lcampoverde.administrativo.cliente.model.User;
import com.lcampoverde.administrativo.cliente.model.nopersist.RoleVO;
import com.lcampoverde.administrativo.cliente.model.nopersist.SignUpRequest;
import com.lcampoverde.administrativo.cliente.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Lazy
public class UserGestorImpl implements UserGestor {

    private static final Logger logger = LoggerFactory.getLogger(UserGestorImpl.class);

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    private UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createUser(SignUpRequest signUpRequest, Role role, Long userId) {
        try {
            // Creating user's account
            User user = User.builder()
                    .firstName(signUpRequest.getFirstName())
                    .lastName(signUpRequest.getLastName())
                    .fullName(signUpRequest.getFirstName().concat(" ").concat(signUpRequest.getLastName()))
                    .userName(signUpRequest.getUserName())
                    .email(signUpRequest.getEmail())
                    .photo(signUpRequest.getPhoto())
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .roles(Collections.singleton(role))
                    .enabled(Boolean.TRUE)
                    .build();
            user.setCreatedAt(new Date());
            user.setCreatedBy(userId);
            saveUser(user);
        } catch (Exception ex) {
            logger.error(LoggerError.SAVE.getConstant(), ex);
            throw new ErrorException(CrudError.SAVE.getConstant());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<SignUpRequest> findByEnabledAndRole(Boolean enabled, Long roleId) {
         return userToVO(userRepository.findByEnabledAndRole(enabled, roleId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<SignUpRequest> findByEnabled(Boolean enabled) {
        return userToVO(userRepository.findByEnabled(enabled));
    }

    private Set<SignUpRequest> userToVO(Set<User> users) {
        return users.stream().map(
            u -> SignUpRequest
                .builder()
                .userName(u.getUserName())
                .lastName(u.getLastName())
                .email(u.getEmail())
                .firstName(u.getFirstName())
                .id(u.getId())
                .fullName(u.getFullName())
                .roles(
                    u.getRoles().stream().map( r ->
                        RoleVO.builder()
                            .description(r.getDescription())
                            .name(r.getName())
                            .id(r.getId())
                            .build()
                    ).collect(Collectors.toSet())
                )
                .build()
        ).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SignUpRequest updateUser(SignUpRequest user) {
        try {
            Optional<User> userSearch = findById(user.getId());
            if(userSearch.isPresent()) {
                userRepository.save(
                    userSearch.get()
                        .toBuilder()
                        .password(null != user.getPassword() ? passwordEncoder.encode(user.getPassword()) : userSearch.get().getPassword())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .fullName(user.getFirstName().concat(" ").concat(user.getLastName()))
                        .build()
                );
                return user;
            } else throw new AppException(UserError.NOT_EXIST.getConstant());

        } catch (AppException aex) {
            throw aex;
        } catch (Exception ex) {
            logger.error(LoggerError.UPDATE.getConstant(), ex);
            throw new ErrorException(CrudError.UPDATE.getConstant());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRepository getUserRepository() {
        return this.userRepository;
    }
}
