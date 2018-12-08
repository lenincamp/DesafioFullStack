package com.lcampoverde.administrativo.cliente.service;

import com.lcampoverde.administrativo.cliente.constant.CatalogKeyWord;
import com.lcampoverde.administrativo.cliente.constant.CatalogValueKeyWord;
import com.lcampoverde.administrativo.cliente.exception.AppException;
import com.lcampoverde.administrativo.cliente.model.nopersist.CatalogValueVO;

import java.util.List;

/**
 * @author lenin
 */
public interface CatalogValueService {
    /**
     * Find catalog values for id catalog.
     * @param catalogKeyWord id of catalog
     * @return list of catalog value.
     */
    List<CatalogValueVO> findByCatalogId(CatalogKeyWord catalogKeyWord);
    /**
     * Save a catalogValue.
     * @param catalogValue catalog value.
     * @return catalog value created.
     */
    CatalogValueVO saveCatalogValue(CatalogValueVO catalogValue);

    /**
     * Delete catalog value by id.
     * @param id id of catalog value.
     */
    void deleteCatalogValueByIdOrCatalogValueKeyWord(Long id, CatalogValueKeyWord catalogValueKeyWord);

    /**
     * Update catalogValue.
     * @param catalogValue catalog value.
     * @return catalog value.
     * @throws AppException exception on duplicated keyword.
     */
    CatalogValueVO updateCatalogValue(CatalogValueVO catalogValue);
}
