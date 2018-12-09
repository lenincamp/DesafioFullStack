package com.lcampoverde.administrativo.ws.authentication;

import com.lcampoverde.administrativo.cliente.constant.ResponseMessages;
import com.lcampoverde.administrativo.cliente.constant.error.CrudError;
import com.lcampoverde.administrativo.cliente.constant.error.UserError;
import com.lcampoverde.administrativo.cliente.model.Role;
import com.lcampoverde.administrativo.cliente.model.User;
import com.lcampoverde.administrativo.cliente.model.nopersist.CustomApiResponse;
import com.lcampoverde.administrativo.cliente.model.nopersist.JwtAuthenticationResponse;
import com.lcampoverde.administrativo.cliente.model.nopersist.LoginRequest;
import com.lcampoverde.administrativo.cliente.model.nopersist.SignUpRequest;
import com.lcampoverde.administrativo.cliente.model.nopersist.UserPrincipal;
import com.lcampoverde.administrativo.cliente.security.JwtTokenProvider;
import com.lcampoverde.administrativo.cliente.service.RoleService;
import com.lcampoverde.administrativo.cliente.service.UserRoleService;
import com.lcampoverde.administrativo.cliente.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static com.lcampoverde.administrativo.cliente.utils.Util.getJwtFromRequest;

/**
 * @author lenin
 * Rest controller for auth
 */
@RestController
@RequestMapping("/api/auth")
@Api(value = "login", description = "Operations login in manageable system.")
public class AuthController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.prefix}")
    private String tokenPrefix;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private UserRoleService userRoleService;

    @Autowired
    @Lazy
    private RoleService roleService;

    @Autowired
    @Lazy
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    @ApiOperation(value = "User login validation.", response = Object.class)
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, (UserPrincipal) authentication.getPrincipal()));
    }

    @PostMapping("/signup")
    @ApiOperation(value = "Register a user.", response = Object.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User registered successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    public ResponseEntity<CustomApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        try {
            if (userService.existsByUserName(signUpRequest.getUserName())) {
                return new ResponseEntity<>(
                    CustomApiResponse.builder().success(Boolean.FALSE).message(UserError.USER_NAME.getConstant()).build(),
                    HttpStatus.BAD_REQUEST
                );
            }

            if (userService.existsByEmail(signUpRequest.getEmail())) {
                return new ResponseEntity<>(
                    CustomApiResponse.builder().success(Boolean.FALSE).message(UserError.EMAIL.getConstant()).build(),
                    HttpStatus.BAD_REQUEST
                );
            }

            Role userRole = roleService.findByName(signUpRequest.getRole());
            userService.createUser(signUpRequest, userRole, tokenProvider.getUserIdFromJWT(getJwtFromRequest(request, tokenHeader, tokenPrefix)));
            Long userId = userService.getUserIdByUserName(signUpRequest.getUserName());
            userRoleService.saveUserRole(userId, userRole.getId());
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/users/{userName}")
                    .buildAndExpand(signUpRequest.getUserName()).toUri();

            return ResponseEntity.created(location).body(
                CustomApiResponse.builder()
                        .success(Boolean.TRUE)
                        .message(ResponseMessages.SAVE_UPDATE.getConstant())
                        .build()
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                CustomApiResponse.builder()
                        .success(Boolean.FALSE)
                        .message(CrudError.SAVE.getConstant())
                        .build(),
                HttpStatus.CONFLICT
            );
        }
    }

    @GetMapping
    @ApiOperation(value = "Refresh token.", response = Object.class)
    public ResponseEntity<JwtAuthenticationResponse> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        Long userId = tokenProvider.getUserIdFromJWT(token);
        Optional<User> user = userService.findUserById(userId);
        if (user.isPresent() && tokenProvider.canTokenBeRefreshed(token, user.get().getLastPasswordResetDate())) {
            String refreshedToken = tokenProvider.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken, null));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
