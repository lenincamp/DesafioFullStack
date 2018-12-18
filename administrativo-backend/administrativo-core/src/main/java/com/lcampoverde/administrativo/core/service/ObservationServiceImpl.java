package com.lcampoverde.administrativo.core.service;

import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.gestor.ObservationGestor;
import com.lcampoverde.administrativo.cliente.model.nopersist.ObservationsVO;
import com.lcampoverde.administrativo.cliente.service.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lenin
 */
@Service
@Lazy
public class ObservationServiceImpl implements ObservationService {
    @Autowired
    @Lazy
    private ObservationGestor observationGestor;

    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public ObservationsVO saveObservation(ObservationsVO observation) {
        return observationGestor.saveObservation(observation);
    }

}
