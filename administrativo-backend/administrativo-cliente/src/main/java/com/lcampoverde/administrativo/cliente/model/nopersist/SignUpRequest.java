package com.lcampoverde.administrativo.cliente.model.nopersist;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@Builder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="User")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SignUpRequest implements Serializable {

    private static final long serialVersionUID = -1767734357011254282L;
    @NotBlank
    @Size(min = 4, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 4, max = 50)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 15)
    private String userName;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    private String fullName;

    @Lob
    private byte[] photo;

    private String role;

    private Long id;

    private Set<RoleVO> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignUpRequest that = (SignUpRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
