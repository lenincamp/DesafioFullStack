package com.lcampoverde.administrativo.core.gestor;

import com.lcampoverde.administrativo.cliente.constant.CatalogValueKeyWord;
import com.lcampoverde.administrativo.cliente.constant.error.CatalogValueError;
import com.lcampoverde.administrativo.cliente.constant.error.CrudError;
import com.lcampoverde.administrativo.cliente.constant.error.LoggerError;
import com.lcampoverde.administrativo.cliente.exception.AppException;
import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.gestor.CatalogValueGestor;
import com.lcampoverde.administrativo.cliente.gestor.StatusGestor;
import com.lcampoverde.administrativo.cliente.model.CatalogValue;
import com.lcampoverde.administrativo.cliente.model.Status;
import com.lcampoverde.administrativo.cliente.model.StatusUser;
import com.lcampoverde.administrativo.cliente.model.StatusUserId;
import com.lcampoverde.administrativo.cliente.model.nopersist.SignUpRequest;
import com.lcampoverde.administrativo.cliente.model.nopersist.StatusVO;
import com.lcampoverde.administrativo.cliente.repository.StatusRepository;
import com.lcampoverde.administrativo.cliente.repository.StatusUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Lazy
public class StatusGestorImpl implements StatusGestor {

    private static final Logger logger = LoggerFactory.getLogger(StatusGestorImpl.class);

    @Autowired
    @Lazy
    private StatusRepository statusRepository;

    @Autowired
    @Lazy
    private CatalogValueGestor catalogValueGestor;

    @Autowired
    @Lazy
    private StatusUserRepository statusUserRepository;


    /**
     * {@inheritDoc}
     */
    @Override
    public StatusVO saveStatus(StatusVO status) {
        try {
            Optional<CatalogValue> catalogValue = catalogValueGestor.findByKeyWord(CatalogValueKeyWord.SA);
            if (catalogValue.isPresent()) {
                Status statusSave =  statusRepository.save(
                    Status.builder()
                        .catalogValueId(catalogValue.get().getId())
                        .executionId(status.getExecutionId())
                        .finishDate(status.getFinishDate())
                        .build()
                );
                Set<Long> idUsers = status.getUsers().stream().map(SignUpRequest::getId).collect(Collectors.toSet());
                idUsers.forEach(id ->
                    statusUserRepository.save(
                    StatusUser.builder()
                        .id(
                            StatusUserId.builder()
                            .statusId(statusSave.getId())
                            .userId(id)
                            .build()
                        )
                        .build()
                    )
                );
                return status.toBuilder().id(statusSave.getId()).build();
            } else throw new AppException(CatalogValueError.NOT_EXIST.getConstant());
        } catch (AppException aex) {
            throw aex;
        } catch (Exception ex) {
            logger.error(LoggerError.SAVE.getConstant(), ex);
            throw new ErrorException(CrudError.SAVE.getConstant());
        }
    }

    @Override
    public void finishStatus(Long id) {
        try {
            Optional<CatalogValue> catalogValue = catalogValueGestor.findByKeyWord(CatalogValueKeyWord.SF);
            if (catalogValue.isPresent()) {
                Optional<Status> st = statusRepository.findById(id);
                if (st.isPresent()) {
                    statusRepository.save(
                        st.get()
                        .toBuilder().finishDate(new Date()).catalogValueId(catalogValue.get().getId()).build()
                    );
                } else throw new AppException("Status not found.");

            } else throw new AppException(CatalogValueError.NOT_EXIST.getConstant());

        } catch (Exception ex) {
            logger.error(LoggerError.UPDATE.getConstant(), ex);
            throw new ErrorException(CrudError.UPDATE.getConstant());
        }

    }
}
