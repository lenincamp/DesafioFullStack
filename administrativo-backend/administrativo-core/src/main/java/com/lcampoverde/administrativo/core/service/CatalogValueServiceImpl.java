package com.lcampoverde.administrativo.core.service;

import com.lcampoverde.administrativo.cliente.constant.CatalogKeyWord;
import com.lcampoverde.administrativo.cliente.constant.CatalogValueKeyWord;
import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.gestor.CatalogValueGestor;
import com.lcampoverde.administrativo.cliente.model.nopersist.CatalogValueVO;
import com.lcampoverde.administrativo.cliente.service.CatalogValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Lazy
public class CatalogValueServiceImpl implements CatalogValueService {

    @Autowired
    @Lazy
    private CatalogValueGestor catalogValueGestor;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CatalogValueVO> findByCatalogId(CatalogKeyWord catalogKeyWord) {
        return catalogValueGestor.findByCatalogId(catalogKeyWord);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public CatalogValueVO saveCatalogValue(CatalogValueVO catalogValue) {
        return catalogValueGestor.save(catalogValue);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public void deleteCatalogValueByIdOrCatalogValueKeyWord(Long id, CatalogValueKeyWord catalogValueKeyWord) {
        catalogValueGestor.deleteCatalogValueByIdOrCatalogValueKeyWord(id,catalogValueKeyWord);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = ErrorException.class)
    public CatalogValueVO updateCatalogValue(CatalogValueVO catalogValue) {
        return catalogValueGestor.update(catalogValue);
    }
}
