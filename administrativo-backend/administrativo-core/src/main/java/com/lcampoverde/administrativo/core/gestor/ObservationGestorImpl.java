package com.lcampoverde.administrativo.core.gestor;

import com.lcampoverde.administrativo.cliente.constant.CatalogValueKeyWord;
import com.lcampoverde.administrativo.cliente.constant.error.CatalogValueError;
import com.lcampoverde.administrativo.cliente.constant.error.CrudError;
import com.lcampoverde.administrativo.cliente.constant.error.LoggerError;
import com.lcampoverde.administrativo.cliente.exception.AppException;
import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.gestor.CatalogValueGestor;
import com.lcampoverde.administrativo.cliente.gestor.ObservationGestor;
import com.lcampoverde.administrativo.cliente.model.CatalogValue;
import com.lcampoverde.administrativo.cliente.model.Observations;
import com.lcampoverde.administrativo.cliente.model.nopersist.ObservationsVO;
import com.lcampoverde.administrativo.cliente.repository.ObservationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author lenin
 */
@Component
@Lazy
public class ObservationGestorImpl implements ObservationGestor {

    private static final Logger logger = LoggerFactory.getLogger(ObservationGestorImpl.class);

    @Autowired
    @Lazy
    private ObservationsRepository observationsRepository;

    @Autowired
    @Lazy
    private CatalogValueGestor catalogValueGestor;

    @Override
    public ObservationsVO saveObservation(ObservationsVO observation) {
        try {
            //Observation type default
            Optional<CatalogValue> catalogValue = catalogValueGestor.findByKeyWord(CatalogValueKeyWord.OD);
            if (catalogValue.isPresent()) {
                Observations obs = observationsRepository.save(
                    Observations
                        .builder()
                        .statusId(observation.getStatusId())
                        .userId(observation.getUserId())
                        .catalogValueId(catalogValue.get().getId())
                        .description(observation.getDescription())
                        .build()
                );
                return observation.toBuilder().id(obs.getId()).build();
            } else {
                throw new AppException(CatalogValueError.NOT_EXIST.getConstant());
            }

        } catch (Exception ex) {
            logger.error(LoggerError.SAVE.getConstant(), ex);
            throw new ErrorException(CrudError.SAVE.getConstant());
        }
    }
}
