package com.lcampoverde.administrativo.core.gestor;

import com.lcampoverde.administrativo.cliente.constant.error.CatalogError;
import com.lcampoverde.administrativo.cliente.constant.error.CrudError;
import com.lcampoverde.administrativo.cliente.constant.error.LoggerError;
import com.lcampoverde.administrativo.cliente.exception.AppException;
import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.gestor.CatalogGestor;
import com.lcampoverde.administrativo.cliente.model.Catalog;
import com.lcampoverde.administrativo.cliente.repository.CatalogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author lenin
 */
@Component
@Lazy
public class CatalogGestorImpl implements CatalogGestor {

    private static final Logger logger = LoggerFactory.getLogger(CatalogGestorImpl.class);

    @Autowired
    @Lazy
    private CatalogRepository catalogRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public CatalogRepository getCatalogRepository() {
        return catalogRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog update(Catalog catalog) {
        try {
            Optional<Catalog> catalogOptional = findByKeyWord(catalog.getKeyWord());
            if (catalogOptional.isPresent()) {
                return save(
                        catalogOptional.get().toBuilder()
                        .description(catalog.getDescription())
                        .endDate(catalog.getEndDate())
                        .keyWord(catalog.getKeyWord())
                        .name(catalog.getName()).build()
                );
            } else {
                throw new AppException(CatalogError.NOT_EXIST.getConstant());
            }
        } catch (AppException aex) {
            throw aex;
        } catch (Exception ex) {
            logger.error(LoggerError.UPDATE.getConstant(), ex);
            throw new ErrorException(CrudError.UPDATE.getConstant());
        }
    }
}
