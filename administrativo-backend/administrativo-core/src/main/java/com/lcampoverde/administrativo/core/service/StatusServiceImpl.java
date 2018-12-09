package com.lcampoverde.administrativo.core.service;

import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.gestor.StatusGestor;
import com.lcampoverde.administrativo.cliente.model.nopersist.StatusVO;
import com.lcampoverde.administrativo.cliente.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lenin
 */
@Service
@Lazy
public class StatusServiceImpl implements StatusService {
    @Autowired
    @Lazy
    private StatusGestor statusGestor;

    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public StatusVO saveStatus(StatusVO status) {
        return statusGestor.saveStatus(status);
    }

    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public void finishStatus(Long id) {
        statusGestor.finishStatus(id);
    }
}
