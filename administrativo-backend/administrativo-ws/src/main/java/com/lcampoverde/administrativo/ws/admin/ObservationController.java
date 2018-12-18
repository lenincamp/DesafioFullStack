package com.lcampoverde.administrativo.ws.admin;

import com.lcampoverde.administrativo.cliente.constant.ResponseMessages;
import com.lcampoverde.administrativo.cliente.model.nopersist.CustomApiResponse;
import com.lcampoverde.administrativo.cliente.model.nopersist.ObservationsVO;
import com.lcampoverde.administrativo.cliente.service.ObservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * @author lenin
 * Rest controller for catalog value.
 */
@RestController
@RequestMapping("/api/process/execution/status/observation")
@Api(value = "Observation for status in executions process", description = "Observation.")
@PreAuthorize("hasRole('USER_FINAL')")
public class ObservationController {

    @Autowired
    @Lazy
    private ObservationService observationService;

    @PostMapping("/create")
    @ApiOperation(value = "Create Observation for status and user.", response = CustomApiResponse.class)
    public ResponseEntity<CustomApiResponse> createStatus(@RequestBody ObservationsVO observation) {
        try {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/process/execution/status/observation/{id}")
                    .buildAndExpand(observationService.saveObservation(observation)).toUri();
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
}
