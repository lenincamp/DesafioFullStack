package com.lcampoverde.administrativo.core.gestor;

import com.lcampoverde.administrativo.cliente.gestor.UserGestor;
import com.lcampoverde.administrativo.cliente.model.Role;
import com.lcampoverde.administrativo.cliente.model.User;
import com.lcampoverde.administrativo.cliente.model.nopersist.SignUpRequest;
import com.lcampoverde.administrativo.cliente.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

@Component
@Lazy
public class UserGestorImpl implements UserGestor {

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
        // Creating user's account
        User user = User.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .name(signUpRequest.getFirstName().concat(" ").concat(signUpRequest.getLastName()))
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
    }

    @Override
    public UserRepository getUserRepository() {
        return this.userRepository;
    }
}
