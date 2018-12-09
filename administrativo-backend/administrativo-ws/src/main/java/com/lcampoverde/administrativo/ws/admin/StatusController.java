package com.lcampoverde.administrativo.ws.admin;

import com.lcampoverde.administrativo.cliente.constant.ResponseMessages;
import com.lcampoverde.administrativo.cliente.model.nopersist.CustomApiResponse;
import com.lcampoverde.administrativo.cliente.model.nopersist.StatusVO;
import com.lcampoverde.administrativo.cliente.service.StatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/api/process/execution/status")
@Api(value = "Status of executions process", description = "Status by execution.")
public class StatusController {

    @Autowired
    @Lazy
    private StatusService statusService;

    @PostMapping("/create")
    @ApiOperation(value = "Create status for execution.", response = CustomApiResponse.class)
    public ResponseEntity<CustomApiResponse> createStatus(@RequestBody StatusVO status) {
        try {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/process/execution/status/{id}")
                    .buildAndExpand(statusService.saveStatus(status)).toUri();
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

    @PutMapping("/finish")
    @ApiOperation(value = "Finish status of execution process.", response = CustomApiResponse.class,
            notes = "On error return false and message error for client.", responseContainer = "CustomApiResponse")
    public ResponseEntity<CustomApiResponse> updateStatusFinishUser(@RequestBody StatusVO status) {
        try {
            statusService.finishStatus(status.getId());
            return ResponseEntity.ok(
                CustomApiResponse.builder()
                .success(Boolean.TRUE)
                .message(ResponseMessages.SAVE_UPDATE.getConstant())
                .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.FALSE).message(e.getMessage()).build());
        }
    }
}
