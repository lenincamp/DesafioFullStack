package com.lcampoverde.administrativo.cliente.service;

import com.lcampoverde.administrativo.cliente.model.nopersist.ObservationsVO;

/**
 * @author lenin
 */
public interface ObservationService {
    /**
     * Save observation.
     * @param observation
     * @return observation persist.
     */
    ObservationsVO saveObservation(ObservationsVO observation);
}
