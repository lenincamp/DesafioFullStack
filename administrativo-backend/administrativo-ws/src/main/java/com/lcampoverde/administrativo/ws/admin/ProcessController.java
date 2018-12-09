package com.lcampoverde.administrativo.ws.admin;

import com.lcampoverde.administrativo.cliente.constant.ResponseMessages;
import com.lcampoverde.administrativo.cliente.model.nopersist.CustomApiResponse;
import com.lcampoverde.administrativo.cliente.model.nopersist.ProcessVO;
import com.lcampoverde.administrativo.cliente.service.ProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
 * Rest controller for process
 */
@RestController
@RequestMapping("/api/process")
@Api(value = "Process", description = "Operations for process.")
public class ProcessController {

    @Autowired
    @Lazy
    private ProcessService processService;

    @PostMapping("/create")
    @ApiOperation(value = "Create process.", response = CustomApiResponse.class)
    public ResponseEntity<CustomApiResponse> cerateProcess(@Valid @RequestBody ProcessVO process) {
        try {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/process/{id}")
                    .buildAndExpand(processService.save(process).getId()).toUri();
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

    @GetMapping("/list_all")
    @ApiOperation(value = "List all process.", response = CustomApiResponse.class,
            notes = "Multiple values of process in dataCol property.", responseContainer = "List<Process>")
    public ResponseEntity<CustomApiResponse> listAllProcess() {
        try {
            return ResponseEntity.ok(
                CustomApiResponse.builder()
                    .success(Boolean.TRUE)
                    .dataCol(new ArrayList<>(processService.findAllProcess()))
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.FALSE).message(e.getMessage()).build());
        }
    }

    @GetMapping("/find_by_edit/{id}")
    @ApiOperation(value = "Get process by id.", response = CustomApiResponse.class,
            notes = "Process in data property.", responseContainer = "Process")
    public ResponseEntity<CustomApiResponse> findProcessById(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    CustomApiResponse.builder()
                            .success(Boolean.TRUE)
                            .data(processService.findProcessById(id))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.FALSE).message(e.getMessage()).build());
        }
    }

    @GetMapping("/find_by_user")
    @ApiOperation(value = "List process by user.", response = CustomApiResponse.class,
            notes = "Multiple values of process in dataCol property.", responseContainer = "List<Process>")
    public ResponseEntity<CustomApiResponse> findProcessByUserId(@NotNull @RequestParam Long userId) {
        try {
            return ResponseEntity.ok(
                    CustomApiResponse.builder()
                        .success(Boolean.TRUE)
                        .dataCol(new ArrayList<>(processService.findByUserId(userId)))
                        .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.FALSE).message(e.getMessage()).build());
        }
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete process by id.", response = CustomApiResponse.class,
            notes = "On error return false and message error for client.", responseContainer = "CustomApiResponse")
    public ResponseEntity<CustomApiResponse> deleteProcess(@NotNull @PathVariable Long id) {
        try {
            processService.deleteProcessById(id);
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
    @ApiOperation(value = "Update process.", response = CustomApiResponse.class,
            notes = "Return process updated on data property, On error return false and message error for client.", responseContainer = "CustomApiResponse")
    public ResponseEntity<CustomApiResponse> updateProcess(@Valid @RequestBody ProcessVO processVO) {
        try {
            return ResponseEntity.ok(
                    CustomApiResponse.builder()
                            .success(Boolean.TRUE)
                            .message(ResponseMessages.SAVE_UPDATE.getConstant())
                            .data(processService.update(processVO))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.FALSE).message(e.getMessage()).build());
        }
    }
}
