package com.lcampoverde.administrativo.cliente.repository;

import com.lcampoverde.administrativo.cliente.model.Execution;
import com.lcampoverde.administrativo.cliente.model.Process;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;


@Repository
@Lazy
public interface ExecutionRepository extends JpaRepository<Execution, Long> {
    /**
     * Validate exists process for name.
     * @param alias name of process.
     * @return
     */
    Boolean existsByAliasIgnoreCase(String alias);

    /**
     * Find process for name.
     * @param alias name of process.
     * @return
     */
    Process findByAlias(String alias);

    /**
     * Find all process.
     * @param enabled status active o inactive.
     * @return all process with condition.
     */
    Set<Execution> findByEnabled(Boolean enabled);

    /**
     * Find executions by process id.
     */
    @Query("select ex from Execution ex join fetch ex.statuses st join fetch st.catalogValue where ex.processId = :processId and st.enabled = true and st.finishDate is null")
    Set<Execution> findExecutionByProcessId(@Param("processId") Long processId);

    /**
     * Find executions by id.
     */
    @Query( "select ex from Execution ex" +
            " join fetch ex.statuses st " +
            " join fetch st.catalogValue cv" +
            " join fetch st.statusUsers su " +
            " join fetch su.user us " +
            " join fetch st.conclusions cc" +
            " join fetch su.observations obs" +
            " join fetch obs.catalogValue cvo" +
            " join fetch st.objectives obj" +
            " where ex.id = :id " +
            " and st.enabled = true " +
            " and cv.enabled=true " +
            " and us.enabled=true " +
            " and cc.enabled=true " +
            " and obs.enabled=true " +
            " and obj.enabled =true " +
            " and cvo.enabled = true "
    )
    Execution findExecutionById(@Param("id") Long id);

    /**
     * Find executions between date.
     * @param ini date init.
     * @param fin date finish
     * @return set of executions.
     */
    Set<Execution> findExecutionsByCreatedAtBetween(Date ini, Date fin);

    /**
     * Find by process id and created at.
     * @param processId id of process.
     * @param ini date init.
     * @param fin date ent.
     * @return set of executions.
     */
    Set<Execution> findExecutionsByProcessIdAndCreatedAtBetween(Long processId, Date ini, Date fin);
}
