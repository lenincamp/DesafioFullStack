package com.lcampoverde.administrativo.cliente.repository;

import com.lcampoverde.administrativo.cliente.constant.CatalogKeyWord;
import com.lcampoverde.administrativo.cliente.constant.CatalogValueKeyWord;
import com.lcampoverde.administrativo.cliente.model.CatalogValue;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@Lazy
public interface CatalogValueRepository extends JpaRepository<CatalogValue, Long> {
    /**
     * Find catalog value by keyword.
     * @param keyWord keyword of catalog value.
     * @return Optional catalog value.
     */
    Optional<CatalogValue> findByKeyWord(CatalogValueKeyWord keyWord);

    /**
     * Validate exists by keyword.
     * @param keyWord keyword of catalog value.
     * @return exists.
     */
    Boolean existsByKeyWord(CatalogValueKeyWord keyWord);

    /**
     * Find all catalogValues for catalog id.
     * @param catalogKeyWord id of catalog.
     * @return list catalog by id catalog.
     */
    @Query("select cv from CatalogValue cv where cv.catalogId = :catalogKeyWord and cv.endDate is null and cv.enabled = true")
    List<CatalogValue> findByCatalogId(@Param("catalogKeyWord") CatalogKeyWord catalogKeyWord);

    /**
     * Find by catalog value id or catalog value keyword
     * @param id identifier of catalog value.
     * @param catalogValueKeyWord keyword of catalog value.
     * @return optional catalog.
     */
    Optional<CatalogValue> findByIdOrKeyWord(Long id, CatalogValueKeyWord catalogValueKeyWord);
}
