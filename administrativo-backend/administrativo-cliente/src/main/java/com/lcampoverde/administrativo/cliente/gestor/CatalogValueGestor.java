package com.lcampoverde.administrativo.cliente.gestor;

import com.lcampoverde.administrativo.cliente.constant.CatalogKeyWord;
import com.lcampoverde.administrativo.cliente.constant.CatalogValueKeyWord;
import com.lcampoverde.administrativo.cliente.constant.error.CatalogValueError;
import com.lcampoverde.administrativo.cliente.constant.error.CrudError;
import com.lcampoverde.administrativo.cliente.constant.error.LoggerError;
import com.lcampoverde.administrativo.cliente.exception.AppException;
import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.model.CatalogValue;
import com.lcampoverde.administrativo.cliente.model.nopersist.CatalogValueVO;
import com.lcampoverde.administrativo.cliente.repository.CatalogValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lenin
 */
public interface CatalogValueGestor {

    Logger logger = LoggerFactory.getLogger(CatalogValueGestor.class);

    /**
     * Get repository role.
     */
    CatalogValueRepository getRepository();

    /**
     * Verify exists.
     * @param keyWord keyword.
     * @return boolean exists.
     */
    default Boolean existsByKeyWord(CatalogValueKeyWord keyWord) {
        return getRepository().existsByKeyWord(keyWord);
    }

    /**
     * Find catalog value by keyword.
     * @param catalogValueKeyWord catalog value keyword.
     * @return catalog value.
     */
    default Optional<CatalogValue> findByKeyWord(CatalogValueKeyWord catalogValueKeyWord) {
        return getRepository().findByKeyWord(catalogValueKeyWord);
    }

    /**
     * Find catalog value by id.
     * @param id identifier of catalog value.
     * @return catalog value.
     */
    default Optional<CatalogValue> findById(Long id) {
        return getRepository().findById(id);
    }

    /**
     * Save a catalogValue.
     * @param catalogValue catalog value.
     * @return catalog value created.
     */
    default CatalogValueVO save(CatalogValueVO catalogValue) {
        try {
            if (existsByKeyWord(CatalogValueKeyWord.valueOf(catalogValue.getKeyWord()))) {
                throw new AppException(CatalogValueError.EXIST_BY_KEYWORD.getConstant());
            }
            return catalogValue.toBuilder().id(
                    getRepository().save(
                            CatalogValue.builder()
                                .description(catalogValue.getDescription())
                                .name(catalogValue.getName())
                                .keyWord(catalogValue.getKeyWord())
                                .catalogId(catalogValue.getCatalogId())
                                .endDate(catalogValue.getEndDate())
                            .build()
                    ).getId()
            ).build();
        } catch (AppException aex) {
            throw aex;
        } catch (Exception ex) {
            logger.error(LoggerError.SAVE.getConstant(), ex);
            throw new ErrorException("error on save catalog value.");
        }
    }

    /**
     * Find all catalog value by catalog id.
     * @return
     */
    default List<CatalogValueVO> findByCatalogId(CatalogKeyWord catalogKeyWord) {
        return getRepository().findByCatalogId(catalogKeyWord).stream()
                .map(cv ->
                    CatalogValueVO.builder()
                        .id(cv.getId())
                            .description(cv.getDescription())
                            .catalogId(cv.getCatalogId())
                            .name(cv.getName())
                            .keyWord(cv.getKeyWord())
                        .build()
                ).collect(Collectors.toList());
    }

    /**
     * Delete catalog value by id.
     * @param id id of catalog value.
     */
    default void deleteCatalogValueByIdOrCatalogValueKeyWord(Long id, CatalogValueKeyWord catalogValueKeyWord) {
        try {
            Optional<CatalogValue> catalogValue = getRepository().findByIdOrKeyWord(id, catalogValueKeyWord);
            if(catalogValue.isPresent()) {
                getRepository().save(catalogValue.get().toBuilder().enabled(Boolean.FALSE).build());
            }
        } catch (Exception ex) {
            logger.error(LoggerError.DELETE.getConstant(), ex);
            throw new ErrorException(CrudError.DELETE.getConstant());
        }

    }

    /**
     * Update catalogValue.
     * @param catalogValue catalog value.
     * @return catalog value.
     * @throws AppException exception on duplicated keyword.
     */
    default CatalogValueVO update(CatalogValueVO catalogValue) {
        try {
            if (existsByKeyWord(CatalogValueKeyWord.valueOf(catalogValue.getKeyWord()))) {
                throw new AppException(CatalogValueError.EXIST_BY_KEYWORD.getConstant());
            }
            Optional<CatalogValue> catalogValueOptional = getRepository().findById(catalogValue.getId());
            if (catalogValueOptional.isPresent()) {
                getRepository().save(
                    catalogValueOptional.get().toBuilder()
                        .description(catalogValue.getDescription())
                        .keyWord(catalogValue.getKeyWord())
                        .name(catalogValue.getName()).build()
                );
                 return catalogValue;
            } else {
                throw new AppException(CatalogValueError.NOT_EXIST.getConstant());
            }
        } catch (AppException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error(LoggerError.UPDATE.getConstant(), e);
            throw new ErrorException(CrudError.UPDATE.getConstant());
        }
    }
}
