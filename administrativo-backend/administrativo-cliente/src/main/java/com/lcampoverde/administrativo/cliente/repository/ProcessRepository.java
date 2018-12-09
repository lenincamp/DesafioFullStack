package com.lcampoverde.administrativo.cliente.repository;

import com.lcampoverde.administrativo.cliente.model.Process;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    @Query("select p from Process p join fetch p.catalogValue cv where u.enabled = :enabled and r.id=roleId")
    Set<Process> findByEnabled(Boolean enabled);
}
