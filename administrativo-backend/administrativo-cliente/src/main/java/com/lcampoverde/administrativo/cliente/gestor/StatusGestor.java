package com.lcampoverde.administrativo.cliente.gestor;

import com.lcampoverde.administrativo.cliente.model.nopersist.StatusVO;

/**
 * @author lenin
 */
public interface StatusGestor {
    /**
     * Save status.
     * @param status
     * @return status persist.
     */
    StatusVO saveStatus(StatusVO status);

    /**
     * Update status
     * @param id
     */
    void finishStatus(Long id);
}
