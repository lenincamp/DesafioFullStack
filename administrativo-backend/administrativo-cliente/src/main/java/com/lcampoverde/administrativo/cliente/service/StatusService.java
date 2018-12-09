package com.lcampoverde.administrativo.cliente.service;

import com.lcampoverde.administrativo.cliente.model.nopersist.StatusVO;

/**
 * @author lenin
 */
public interface StatusService {
    /**
     * Save status.
     * @param status
     * @return status persist.
     */
    StatusVO saveStatus(StatusVO status);

    /**
     * Finish status
     * @param id
     */
    void finishStatus(Long id);
}
