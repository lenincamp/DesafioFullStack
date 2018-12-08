package com.lcampoverde.administrativo.core.service;

import com.lcampoverde.administrativo.cliente.model.User;
import com.lcampoverde.administrativo.cliente.model.nopersist.UserPrincipal;
import com.lcampoverde.administrativo.cliente.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

/**
 * @author lenin
 * Override UserDetailService from load roles permission on authenticate.
 */
@Service
@Lazy
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    @Lazy
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        User user = userRepository.findByUserNameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
                );
        if (!user.getEnabled()) throw new UsernameNotFoundException("User down on system");
        user.getUserGroups().stream().forEach(g ->
                g.getRoles().addAll(
                        null == g.getGroupParent() ?
                                g.getGroupChildren().stream().flatMap(ug -> ug.getRoles().stream()).collect(Collectors.toSet()) :
                                g.getGroupParent().getRoles()
                )
        );
        user.getRoles().addAll(user.getUserGroups().stream().flatMap(userGroup -> userGroup.getRoles().stream()).collect(Collectors.toSet()));
        return UserPrincipal.create(user, Boolean.TRUE);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );
        return UserPrincipal.create(user, Boolean.FALSE);
    }

}
