package com.lcampoverde.administrativo.cliente.service;

import com.lcampoverde.administrativo.cliente.model.Role;
import com.lcampoverde.administrativo.cliente.model.User;
import com.lcampoverde.administrativo.cliente.model.nopersist.SignUpRequest;

import java.util.Optional;

/**
 * @author lenin
 */
public interface UserService {

    /**
     * Save a user.
     * @param user
     * @param role role for user.
     * @param userId user id.
     */
    void createUser(SignUpRequest user, Role role, Long userId);

    /**
     *Get user for user name.
     * @return id of user.
     */
    Long getUserIdByUserName(String userName) ;

    /**
     * Validate if exists user by userName
     * @param userName username of user.
     * @return validation of exists user.
     */
    Boolean existsByUserName(String userName);


    /**
     * Validate if exists user by email.
     * @param userEmail mail of user.
     * @return validation of exists user.
     */
    Boolean existsByEmail(String userEmail);

    /**
     * Save a user.
     * @param user
     */
    void saveUser(User user);

    /**
     * Find user by id.
     * @param userId
     */
    Optional<User> findUserById(Long userId);
}
