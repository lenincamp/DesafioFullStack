package com.lcampoverde.administrativo.cliente.repository;

import com.lcampoverde.administrativo.cliente.model.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Lazy
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUserNameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByUserName(String userName);

    @Query("select u.id from User u where u.userName=:userName")
    Long getdByUserName(@Param("userName") String userName);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

    /**
     * save a user.
     * @param user
     */
    @Modifying
    @Query(value = "insert into USER (fullName,firstName,lastName,userName,email,password, photo, created_at, created_by, enabled) VALUES (:#{#user.fullName},:#{#user.firstName},:#{#user.lastName},:#{#user.userName},:#{#user.email},:#{#user.password},:#{#user.photo},:#{#user.createdAt}, :#{#user.createdBy}, :#{#user.enabled})", nativeQuery =true)
    @Transactional
    void saveUser(@Param("user")User user);

    /**
     * Up or down on system.
     * @param enabled
     * @param id
     */
    @Modifying
    @Query(value = "UPDATE USER SET ENABLED = :enabled WHERE ID = :id", nativeQuery =true)
    @Transactional
    void upDownUser(@Param("enabled")Boolean enabled, @Param("id")Long id);

    /**
     * Get all users actives in the system.
     * @param enabled
     * @return
     */
    @Query("select u from User u join fetch u.roles r where u.enabled = :enabled")
    Set<User> findByEnabled(@Param("enabled") Boolean enabled);

    /**
     * Get users by status and role.
     * @param enabled
     * @param roleId
     * @return
     */
    @Query("select u from User u join fetch u.roles r where u.enabled = :enabled and r.id=:roleId")
    Set<User> findByEnabledAndRole(@Param("enabled")Boolean enabled, @Param("roleId")Long roleId);

}
