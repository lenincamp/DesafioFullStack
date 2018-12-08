package com.lcampoverde.administrativo.cliente.model.nopersist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lcampoverde.administrativo.cliente.model.Role;
import com.lcampoverde.administrativo.cliente.model.User;
import com.lcampoverde.administrativo.cliente.utils.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Builder(toBuilder=true)
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = 5118013137245168347L;
    private final Long id;

    private final String name;

    private final String username;

    @JsonIgnore
    private final String email;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    private Map<String, Object> rolesMap;

    private final boolean enabled;

    @JsonIgnore
    private final Date lastPasswordResetDate;

    public static UserPrincipal create(User user, Boolean getRoles) {
        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user),
                loadDataRoles(user, getRoles),
                user.getEnabled(),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(User user) {
        return user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());
    }

    private static Map<String, Object> loadDataRoles(User user, Boolean getRoles) {
        if (getRoles) {
            Set<Role> roles = user.getRoles().stream().map(Role::new).collect(Collectors.toSet());
            roles.forEach(role -> {
                role.setPermissions(
                        role.getPermissions().stream().filter(Util.distinctByKey(p -> p.getId().getModuleId())).collect(Collectors.toSet())
                );
                role.getPermissions().forEach(p -> p.getModule().getActionsSet().size());
            });

            Map<String, Object> rolesMap = new HashMap<>();
            rolesMap.put("roles", roles);
            rolesMap.put("photo", user.getPhoto());
            return rolesMap;
        } else {
            return null;
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public Map<String, Object> getRolesMap() {
        return rolesMap;
    }

    public void setRolesMap(Map<String, Object> rolesMap) {
        this.rolesMap = rolesMap;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
