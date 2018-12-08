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

    @Modifying
    @Query(value = "insert into USER (name,firstName,lastName,userName,email,password, photo, created_at, created_by, enabled) VALUES (:#{#user.name},:#{#user.firstName},:#{#user.lastName},:#{#user.userName},:#{#user.email},:#{#user.password},:#{#user.photo},:#{#user.createdAt}, :#{#user.createdBy}, :#{#user.enabled})", nativeQuery =true)
    @Transactional
    void saveUser(@Param("user")User user);
}
