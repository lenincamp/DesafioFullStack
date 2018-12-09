package com.lcampoverde.administrativo.ws.admin;

import com.lcampoverde.administrativo.cliente.constant.ResponseMessages;
import com.lcampoverde.administrativo.cliente.model.nopersist.CustomApiResponse;
import com.lcampoverde.administrativo.cliente.model.nopersist.SignUpRequest;
import com.lcampoverde.administrativo.cliente.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lenin
 * Rest controller for users
 */
@RestController
@RequestMapping("/api/user")
@Api(value = "User", description = "Operations for user.")
public class UserController {

    @Autowired
    @Lazy
    private UserService userService;

    @GetMapping("/find")
    @ApiOperation(value = "List users for status or status and roles.", response = CustomApiResponse.class,
            notes = "Multiple values of execution users in dataCol property.", responseContainer = "List<User>")
    public ResponseEntity<CustomApiResponse> findUserByEnabledAndRole(
            @RequestParam(value="enabled",required=false) Boolean enabled,
            @RequestParam(value="roleId",required=false) Long roleId
    ) {
        try {
            Set<SignUpRequest> users = new HashSet<>();
            if (null != enabled && null != roleId)
                users = userService.findByEnabledAndRole(enabled, roleId);
            else if (null != enabled)
                users = userService.findByEnabled(enabled);
            else
                ResponseMessages.NO_FILTERS.getConstant();
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.TRUE).dataCol(new ArrayList<>(users)).build());
        } catch (Exception e) {
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.FALSE).message(e.getMessage()).build());
        }
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete user by id.", response = CustomApiResponse.class,
            notes = "On error return false and message error for client.", responseContainer = "CustomApiResponse")
    public ResponseEntity<CustomApiResponse> deleteUserById(
            @RequestParam Long id
    ) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.TRUE).message(ResponseMessages.DELETE.getConstant()).build());
        } catch (Exception e) {
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.FALSE).message(e.getMessage()).build());
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update execution process by id.", response = CustomApiResponse.class,
            notes = "Return execution process updated on data property, On error return false and message error for client.", responseContainer = "CustomApiResponse")
    public ResponseEntity<CustomApiResponse> updateUserById(@RequestBody SignUpRequest user) {
        try {
            return ResponseEntity.ok(
                CustomApiResponse.builder()
                    .success(Boolean.TRUE)
                    .message(ResponseMessages.SAVE_UPDATE.getConstant())
                    .data(userService.updateUser(user))
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(CustomApiResponse.builder().success(Boolean.FALSE).message(e.getMessage()).build());
        }
    }
}
