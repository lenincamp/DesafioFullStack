package com.lcampoverde.administrativo.cliente.repository;

import com.lcampoverde.administrativo.cliente.constant.CatalogKeyWord;
import com.lcampoverde.administrativo.cliente.model.Catalog;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Lazy
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    Optional<Catalog> findByKeyWord(CatalogKeyWord keyWord);
}
