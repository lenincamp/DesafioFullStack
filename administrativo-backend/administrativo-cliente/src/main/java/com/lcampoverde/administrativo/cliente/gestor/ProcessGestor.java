package com.lcampoverde.administrativo.cliente.gestor;

import com.lcampoverde.administrativo.cliente.model.nopersist.ProcessVO;
import com.lcampoverde.administrativo.cliente.repository.ProcessRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author lenin
 */
public interface ProcessGestor {
    /**
     * Get repository role.
     */
    ProcessRepository getProcessRepository();

    /**
     * Verify exists process.
     * @param processName
     * @return
     */
    default Boolean existsByName(String processName) {
        return getProcessRepository().existsByNameIgnoreCase(processName);
    }

    /**
     * Save a process.
     * @param process
     * @return
     */
    ProcessVO save(ProcessVO process);

    /**
     * Find all process.
     * @return
     */
    Collection<ProcessVO> findAllProcess();

    /**
     * Find process by id.
     * @return
     */
    ProcessVO findProcessById(Long id);

    /**
     * Find process by name.
     * @return
     */
    ProcessVO findProcessByName(String name);

    /**
     * Delete process by id
     * @param id
     */
    void deleteProcessById(Long id);

    /**
     * Delete process by name.
     * @param name
     */
    void deleteProcessByName(String name);

    /**
     * Updated a process.
     * @param processVO process of request.
     * @return process updated.
     */
    ProcessVO update(ProcessVO processVO);

    /**
     * Find process by userId
     * @param userId
     * @return
     */
    List<ProcessVO> findByUserId(Long userId);

    /**
     * get process with all relations.
     * @return
     */
    List<ProcessVO> findByProcessId(Long processId);
}
