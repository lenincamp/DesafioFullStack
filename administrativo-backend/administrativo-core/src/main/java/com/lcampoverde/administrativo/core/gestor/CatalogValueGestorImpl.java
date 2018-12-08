package com.lcampoverde.administrativo.core.gestor;

import com.lcampoverde.administrativo.cliente.gestor.CatalogValueGestor;
import com.lcampoverde.administrativo.cliente.repository.CatalogValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author lenin
 */
@Component
@Lazy
public class CatalogValueGestorImpl implements CatalogValueGestor{

    @Autowired
    @Lazy
    private CatalogValueRepository catalogValueRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public CatalogValueRepository getRepository() {
        return catalogValueRepository;
    }
}
