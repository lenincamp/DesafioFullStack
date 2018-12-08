package com.lcampoverde.administrativo.core.service;

import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.gestor.ProcessGestor;
import com.lcampoverde.administrativo.cliente.model.nopersist.ProcessVO;
import com.lcampoverde.administrativo.cliente.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Lazy
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    @Lazy
    private ProcessGestor processGestor;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public ProcessVO save(ProcessVO process) {
        return processGestor.save(process);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<ProcessVO> findAllProcess() {
        return processGestor.findAllProcess();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessVO findProcessById(Long id) {
        return processGestor.findProcessById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessVO findProcessByName(String name) {
        return processGestor.findProcessByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public void deleteProcessById(Long id) {
        processGestor.deleteProcessById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public void deleteProcessByName(String name) {
        processGestor.deleteProcessByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean existsByName(String name) {
        return processGestor.existsByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public ProcessVO update(ProcessVO processVO) {
        return processGestor.update(processVO);
    }
}
