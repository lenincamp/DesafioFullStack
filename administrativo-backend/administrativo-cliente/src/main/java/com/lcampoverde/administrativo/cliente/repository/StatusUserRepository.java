package com.lcampoverde.administrativo.cliente.repository;

import com.lcampoverde.administrativo.cliente.model.StatusUser;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Lazy
public interface StatusUserRepository extends JpaRepository<StatusUser, Long> {
}
