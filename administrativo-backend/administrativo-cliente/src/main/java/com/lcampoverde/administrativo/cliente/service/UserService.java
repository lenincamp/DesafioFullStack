package com.lcampoverde.administrativo.cliente.service;

import com.lcampoverde.administrativo.cliente.model.Role;
import com.lcampoverde.administrativo.cliente.model.User;
import com.lcampoverde.administrativo.cliente.model.nopersist.SignUpRequest;

import java.util.Optional;
import java.util.Set;

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

    /**
     * Delete user by id.
     * @param userId
     */
    void deleteUserById(Long userId);

    /**
     * Get users by status(active, inactive) and role id.
     * @param enabled status of user.
     * @param roleId role by search users.
     * @return list of user pojo.
     */
    Set<SignUpRequest> findByEnabledAndRole(Boolean enabled, Long roleId) ;

    /**
     * Find all users active in the system.
     * @param enabled status of user search.
     * @return list of user pojo.
     */
    Set<SignUpRequest> findByEnabled(Boolean enabled) ;

    /**
     * Update user.
     * @param user user pojo.
     * @param userId userId.
     * @param roleId roleId to update.
     * @return user updated.
     */
    SignUpRequest updateUser(Long userId, Long roleId, SignUpRequest user);
}
