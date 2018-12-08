package com.lcampoverde.administrativo.cliente.gestor;

import com.lcampoverde.administrativo.cliente.constant.error.CrudError;
import com.lcampoverde.administrativo.cliente.constant.error.LoggerError;
import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.model.Role;
import com.lcampoverde.administrativo.cliente.model.User;
import com.lcampoverde.administrativo.cliente.model.nopersist.SignUpRequest;
import com.lcampoverde.administrativo.cliente.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author lenin
 */
public interface UserGestor {
    Logger logger = LoggerFactory.getLogger(UserGestor.class);

    /**
     * Get repository user.
     * @return repository users.
     */
    UserRepository getUserRepository();

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
    default Long getUserIdByUserName(String userName) {
        return getUserRepository().getdByUserName(userName);
    }

    /**
     * Validate if exists user by userName
     * @param userName username of user.
     * @return validation of exists user.
     */
    default Boolean existsByUserName(String userName) {
        return getUserRepository().existsByUserName(userName);
    }


    /**
     * Validate if exists user by email.
     * @param userEmail mail of user.
     * @return validation of exists user.
     */
    default Boolean existsByEmail(String userEmail){
        return getUserRepository().existsByEmail(userEmail);
    }

    /**
     * Save a user.
     * @param user
     */
    default void saveUser(User user){
        try {
            getUserRepository().saveUser(user);
        } catch (Exception ex) {
            logger.error(LoggerError.SAVE.getConstant(), ex);
            throw new ErrorException(CrudError.SAVE.getConstant());
        }
    }

    /**
     * Find user by id.
     * @param userId
     */
    default Optional<User> findById(Long userId){
        return getUserRepository().findById(userId);
    }
}
