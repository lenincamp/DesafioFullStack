package com.lcampoverde.administrativo.ws.admin;

import com.lcampoverde.administrativo.cliente.constant.ResponseMessages;
import com.lcampoverde.administrativo.cliente.exception.AppException;
import com.lcampoverde.administrativo.cliente.model.nopersist.CustomApiResponse;
import com.lcampoverde.administrativo.cliente.model.nopersist.ExecutionVO;
import com.lcampoverde.administrativo.cliente.service.ExecutionService;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lenin
 * Rest controller for execution process.
 */
@RestController
@RequestMapping("/api/process/execution")
@Api(value = "Execution", description = "Executions of process.")
public class ExecutionController {

    @Autowired
    @Lazy
    private ExecutionService executionService;

    @PostMapping("/create")
    @ApiOperation(value = "Create execution process.", response = CustomApiResponse.class)
    public ResponseEntity<CustomApiResponse> createExecutionProcess(@RequestBody ExecutionVO executionVO) {
        try {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/process/execution/{id}")
                    .buildAndExpand(executionService.save(executionVO).getId()).toUri();
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

    @GetMapping("/find_execution")
    @ApiOperation(value = "List all execution by process id, process id and dates or dates, or id.", response = CustomApiResponse.class,
            notes = "Multiple values of execution process in dataCol property.", responseContainer = "List<Execution>")
    public ResponseEntity<CustomApiResponse> findExecutionByProcessId(
            @RequestParam(value="processId",required=false) Long processId,
            @RequestParam(value="id",required=false) Long id,
            @RequestParam(value="dateIni",required=false) Date dateIni,
            @RequestParam(value="dateFin",required=false) Date dateFin
    ) {
        try {
            Set<ExecutionVO> executions = new HashSet<>();
            if (null != processId) {
                if(null != dateIni)
                    executions = executionService.findExecutionByProcessIdAndDate(processId, dateIni, dateFin);
                else executions = executionService.findExecutionByProcessIdAndDate(processId, null, null);
            } else if (null != id)
                executions = new HashSet<>(Arrays.asList(executionService.findExecutionById(id)));
            else if (null != dateIni)
                executions = executionService.findExecutionByDate(dateIni, dateFin);
            else
                ResponseMessages.NO_FILTERS.getConstant();
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.TRUE).dataCol(new ArrayList<>(executions)).build());
        } catch (Exception e) {
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.FALSE).message(e.getMessage()).build());
        }
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete execution process by id or process id.", response = CustomApiResponse.class,
            notes = "On error return false and message error for client.", responseContainer = "CustomApiResponse")
    public ResponseEntity<CustomApiResponse> deleteExecutionProcess(
            @RequestParam(value="id",required=false) Long id,
            @RequestParam(value="processId",required=false) Long processId
    ) {
        try {
            if (null != id)
                executionService.deleteExecutionById(id);
            else if (null!=processId)
                executionService.deleteExecutionByProcessId(processId);
            else
                throw new AppException(ResponseMessages.NO_FILTERS.getConstant());
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.TRUE).message(ResponseMessages.DELETE.getConstant()).build());
        } catch (Exception e) {
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.FALSE).message(e.getMessage()).build());
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update execution process by id.", response = CustomApiResponse.class,
            notes = "Return execution process updated on data property, On error return false and message error for client.", responseContainer = "CustomApiResponse")
    public ResponseEntity<CustomApiResponse> updateExecutionProcess(@RequestBody ExecutionVO executionVO) {
        try {
            return ResponseEntity.ok(
                CustomApiResponse.builder()
                        .success(Boolean.TRUE)
                        .message(ResponseMessages.SAVE_UPDATE.getConstant())
                        .data(executionService.updateExecutionProcess(executionVO))
                        .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.FALSE).message(e.getMessage()).build());
        }
    }
}
