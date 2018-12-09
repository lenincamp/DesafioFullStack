package com.lcampoverde.administrativo.cliente.gestor;

import com.lcampoverde.administrativo.cliente.model.nopersist.ObservationsVO;

/**
 * @author lenin
 */
public interface ObservationGestor {
    /**
     * Save observation for status by id user.
     * @param observation observation.
     * @return observation persist.
     */
    ObservationsVO saveObservation(ObservationsVO observation);
}
