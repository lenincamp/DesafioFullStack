package com.lcampoverde.administrativo.cliente.model.nopersist;

import com.lcampoverde.administrativo.cliente.constant.SecurityConstants;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String accessToken;
    private String tokenType = SecurityConstants.TOKEN_TYPE.getConstant();
    private UserPrincipal userPrincipal;

    public JwtAuthenticationResponse(String accessToken, UserPrincipal userPrincipal) {
        this.accessToken = accessToken;
        this.userPrincipal = userPrincipal;
    }


}
