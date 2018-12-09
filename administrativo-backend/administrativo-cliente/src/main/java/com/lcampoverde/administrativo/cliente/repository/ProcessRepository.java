package com.lcampoverde.administrativo.cliente.repository;

import com.lcampoverde.administrativo.cliente.model.Process;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
@Lazy
public interface ProcessRepository extends JpaRepository<Process, Long> {
    /**
     * Validate exists process for name.
     * @param name name of process.
     * @return
     */
    Boolean existsByNameIgnoreCase(String name);

    /**
     * Find process for name.
     * @param name name of process.
     * @return
     */
    Process findByName(String name);

    /**
     * Find all process.
     * @param enabled status active o inactive.
     * @return all process with condition.
     */
    @Query("select p from Process p join fetch p.catalogValue cv where p.enabled = :enabled")
    Set<Process> findByEnabled(@Param("enabled")Boolean enabled);

    @Query("  select p from Process p " +
            " join fetch p.catalogValue cv " +
            " join fetch p.executions ex " +
            " join fetch ex.statuses st " +
            " join fetch st.statusUsers su " +
            " join fetch su.user u " +
            " join fetch su.observations o" +
            " where p.enabled=true " +
            " and ex.enabled=true " +
            " and u.id=:userId " +
            " and count(o.id) = 0 ")
    Set<Process> findByUserId(@Param("userId")Long userId);
}
