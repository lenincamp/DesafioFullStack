package com.lcampoverde.administrativo.ws.admin;

import com.lcampoverde.administrativo.cliente.constant.CatalogKeyWord;
import com.lcampoverde.administrativo.cliente.constant.ResponseMessages;
import com.lcampoverde.administrativo.cliente.model.nopersist.CatalogValueVO;
import com.lcampoverde.administrativo.cliente.model.nopersist.CustomApiResponse;
import com.lcampoverde.administrativo.cliente.service.CatalogValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.ArrayList;

/**
 * @author lenin
 * Rest controller for catalog value.
 */
@RestController
@RequestMapping("/api/catalog/catalogValue")
@Api(value = "Catalog value", description = "Catalog value of catalogs.")
public class CatalogValueController {

    @Autowired
    @Lazy
    private CatalogValueService catalogValueService;

    @PostMapping("/create")
    @ApiOperation(value = "Create catalog value.", response = CustomApiResponse.class)
    public ResponseEntity<CustomApiResponse> createCatalogValue(@Valid @RequestBody CatalogValueVO catalogValue) {
        try {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/process/catalog/catalogValue/{id}")
                    .buildAndExpand(catalogValueService.saveCatalogValue(catalogValue)).toUri();
            return ResponseEntity.created(location).body(
                    CustomApiResponse.builder()
                            .success(Boolean.TRUE)
                            .message(ResponseMessages.SAVE_UPDATE.getConstant())
                            .build()
            );

        } catch (Exception ex) {
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.FALSE).message(ex.getMessage()).build());
        }
    }

    @GetMapping("/find")
    @ApiOperation(value = "List catalog value by catalogId.", response = CustomApiResponse.class,
            notes = "Multiple values of catalogValue in dataCol property.", responseContainer = "List<CatalogValue>")
    public ResponseEntity<CustomApiResponse> findCatalogValueByCatalogId(
           @NotNull @RequestParam CatalogKeyWord catalogKeyWord
    ) {
        try {
            return ResponseEntity.ok(
                    CustomApiResponse.builder()
                            .success(Boolean.TRUE)
                            .dataCol(new ArrayList<>(catalogValueService.findByCatalogId(catalogKeyWord)))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.FALSE).message(e.getMessage()).build());
        }
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete catalogValue by id.", response = CustomApiResponse.class,
            notes = "On error return false and message error for client.", responseContainer = "CustomApiResponse")
    public ResponseEntity<CustomApiResponse> deleteCatalogValueById(@NotNull @RequestParam Long id) {
        try {
           catalogValueService.deleteCatalogValueByIdOrCatalogValueKeyWord(id, null);
            return ResponseEntity.ok(
                    CustomApiResponse.builder()
                            .success(Boolean.TRUE)
                            .message(ResponseMessages.DELETE.getConstant())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.FALSE).message(e.getMessage()).build());
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update catalogValue by id.", response = CustomApiResponse.class,
            notes = "Return execution process updated on data property, On error return false and message error for client.", responseContainer = "CustomApiResponse")
    public ResponseEntity<CustomApiResponse> updateCatalogValueById(@Valid @RequestBody CatalogValueVO catalogValue) {
        try {
            return ResponseEntity.ok(
                CustomApiResponse.builder()
                        .success(Boolean.TRUE)
                        .message(ResponseMessages.SAVE_UPDATE.getConstant())
                        .data(catalogValueService.updateCatalogValue(catalogValue))
                        .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.FALSE).message(e.getMessage()).build());
        }
    }
}
