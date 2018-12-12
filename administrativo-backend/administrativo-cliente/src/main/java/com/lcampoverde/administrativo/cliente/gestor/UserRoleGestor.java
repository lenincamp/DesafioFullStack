package com.lcampoverde.administrativo.cliente.gestor;

import com.lcampoverde.administrativo.cliente.constant.error.CrudError;
import com.lcampoverde.administrativo.cliente.constant.error.LoggerError;
import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.model.UserRoles;
import com.lcampoverde.administrativo.cliente.model.UserRolesId;
import com.lcampoverde.administrativo.cliente.repository.UserRolesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author lenin
 */
public interface UserRoleGestor {

    Logger logger = LoggerFactory.getLogger(UserGestor.class);

    /**
     * Get repository.
     * @return repository.
     */
    UserRolesRepository getUserRolesRepository();
    /**
     * Save a roles for user.
     * @param userId identifier of user.
     * @param roleId identifier of role.
     */
    default void saveUserRole(Long userId, Long roleId) {
        try {
            getUserRolesRepository().save(UserRoles.builder().id(new UserRolesId(userId, roleId)).build());
        } catch (Exception ex) {
            logger.error(LoggerError.SAVE.getConstant(), ex);
            throw new ErrorException(CrudError.SAVE.getConstant());
        }
    }

    default void updateUserRole(Long userId, Long roleId, Long roleIdNew) {
        try {
            Optional<UserRoles> userRole = getUserRolesRepository().findById_UserIdAndId_RoleId(userId, roleId);
            if (userRole.isPresent()) {
                getUserRolesRepository().save(userRole.get().toBuilder().id(new UserRolesId(userId, roleIdNew)).build());
            }
        } catch (Exception ex) {
            logger.error(LoggerError.UPDATE.getConstant(), ex);
            throw new ErrorException(CrudError.UPDATE.getConstant());
        }
    }
}
