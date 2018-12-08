package com.lcampoverde.administrativo.cliente.gestor;

import com.lcampoverde.administrativo.cliente.constant.CatalogKeyWord;
import com.lcampoverde.administrativo.cliente.constant.error.CatalogError;
import com.lcampoverde.administrativo.cliente.constant.error.CrudError;
import com.lcampoverde.administrativo.cliente.constant.error.LoggerError;
import com.lcampoverde.administrativo.cliente.exception.AppException;
import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.model.Catalog;
import com.lcampoverde.administrativo.cliente.repository.CatalogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * @author lenin
 */
public interface CatalogGestor {

    Logger logger = LoggerFactory.getLogger(CatalogGestor.class);

    /**
     * Get repository.
     */
    CatalogRepository getCatalogRepository();

    default Optional<Catalog> findByKeyWord(CatalogKeyWord catalogKeyWord) {
        return getCatalogRepository().findByKeyWord(catalogKeyWord);
    }

    /**
     * Create new catalog.
     * @param catalog new catalog.
     * @return new catalog.
     */
    default Catalog save(Catalog catalog){
        try {
            return getCatalogRepository().save(catalog);
        } catch (Exception ex) {
            logger.error(LoggerError.SAVE.getConstant(), ex);
            throw new ErrorException(CrudError.SAVE.getConstant());
        }
    }

    /**
     * Return all catalogs.
     * @return list with all catalogs.
     */
    default List<Catalog> getAllCatalogs() {
        return getCatalogRepository().findAll();
    }

    /**
     * Unsubscribe in the system.
     * @param catalogKeyWord id of catalog.
     */
    default void delete(CatalogKeyWord catalogKeyWord) {
        try {
            Optional<Catalog> catalog = findByKeyWord(catalogKeyWord);
            if (catalog.isPresent()) {
                save(catalog.get().toBuilder().enabled(Boolean.FALSE).build());
            } else {
                throw new AppException(CatalogError.NOT_EXIST.getConstant());
            }
        } catch (AppException aex) {
            throw aex;
        } catch (Exception ex) {
            logger.error(LoggerError.DELETE.getConstant(), ex);
            throw new ErrorException(CrudError.DELETE.getConstant());
        }
    }

    /**
     * Update catalog by id.
     * @param catalog catalog with new values.
     * @return catalog updated.
     */
    Catalog update(Catalog catalog);
}
