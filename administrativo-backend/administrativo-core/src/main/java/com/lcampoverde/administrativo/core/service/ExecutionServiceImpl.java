package com.lcampoverde.administrativo.core.service;

import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.gestor.ExecutionGestor;
import com.lcampoverde.administrativo.cliente.model.nopersist.ExecutionVO;
import com.lcampoverde.administrativo.cliente.service.ExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

@Service
@Lazy
public class ExecutionServiceImpl implements ExecutionService {

    @Autowired
    @Lazy
    private ExecutionGestor executionGestor;

    /**
     *{@inheritDoc}
     */
    @Override
    public Boolean existsByAlias(String alias) {
        return executionGestor.existsByAlias(alias);
    }
    /**
     *{@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public ExecutionVO save(ExecutionVO execution) {
        return executionGestor.save(execution);
    }
    /**
     *{@inheritDoc}
     */
    @Override
    public Set<ExecutionVO> findExecutionByProcessIdAndDate(Long processId, Date dateIni, Date dateEnd) {
        return executionGestor.findExecutionByProcessIdAndDate(processId, dateIni, dateEnd);
    }
    /**
     *{@inheritDoc}
     */
    @Override
    public ExecutionVO findExecutionById(Long id) {
        return executionGestor.findExecutionById(id);
    }
    /**
     *{@inheritDoc}
     */
    @Override
    public Set<ExecutionVO> findExecutionByDate(Date dateIni, Date dateEnd) {
        return executionGestor.findExecutionByDate(dateIni, dateEnd);
    }
    /**
     *{@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public void deleteExecutionById(Long id) {
        executionGestor.deleteExecutionById(id);
    }

    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public void deleteExecutionByProcessId(Long processId) {
        executionGestor.deleteExecutionByProcessId(processId);
    }
    /**
     *{@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public ExecutionVO updateExecutionProcess(ExecutionVO execution) {
        return executionGestor.update(execution);
    }
}
