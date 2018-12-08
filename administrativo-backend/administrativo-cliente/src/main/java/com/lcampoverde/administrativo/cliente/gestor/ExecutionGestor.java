package com.lcampoverde.administrativo.cliente.gestor;

import com.lcampoverde.administrativo.cliente.model.nopersist.ExecutionVO;
import com.lcampoverde.administrativo.cliente.repository.ExecutionRepository;

import java.util.Date;
import java.util.Set;

/**
 * @author lenin
 */
public interface ExecutionGestor {
    /**
     * Get repository role.
     */
    ExecutionRepository getRepository();

    /**
     * Verify exists execution of process.
     * @param alias alias of execution
     * @return validation exist.
     */
    default Boolean existsByAlias(String alias) {
        return getRepository().existsByAliasIgnoreCase(alias);
    }

    /**
     * Save a execution.
     * @param execution
     * @return
     */
    ExecutionVO save(ExecutionVO execution);

    /**
     * Find executions by process id or by id and range of date.
     * @return
     */
    Set<ExecutionVO> findExecutionByProcessIdAndDate(Long processId, Date dateIni, Date dateEnd);

    /**
     * Find execution by id.
     * @return
     */
    ExecutionVO findExecutionById(Long id);

    /**
     * Find execution by createdDate or finishDate.
     * @return list of execution by range of date
     */
    Set<ExecutionVO> findExecutionByDate(Date dateIni, Date dateEnd);

    /**
     * Delete execution by id
     * @param id
     */
    void deleteExecutionById(Long id);

    /**
     * Delete execution by process id
     * @param processId
     */
    void deleteExecutionByProcessId(Long processId);

    /**
     * Updated a execution of process.
     * @param execution
     * @return execution updated.
     */
    ExecutionVO update(ExecutionVO execution);

}
