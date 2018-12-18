package com.lcampoverde.administrativo.cliente.dao;

import com.lcampoverde.administrativo.cliente.model.StatusUser;

import java.util.List;

public interface StatusUserDao {
    /**
     * Find status by user id.
     * @param userId id of user.
     * @return list details status user.
     */
    List<StatusUser> findByUserId(Long userId);

    /**
     * Find status user by id process.
     * @param processId id of process.
     * @return list details status user.
     */
    List<StatusUser> findByProcessId(Long processId);
}
