package br.com.ferreiradev.fmu.core.infrastructure.security;

import br.com.ferreiradev.fmu.core.application.mapper.UserMapper;
import br.com.ferreiradev.fmu.core.domain.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;


@RequiredArgsConstructor
@Getter
public class CustomAuthentication implements Authentication {

    private final User user;
    private final UserMapper mapper;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getDetails() {
        return mapper.toRecord(user);
    }

    @Override
    public Object getPrincipal() {
        return mapper.toRecord(user);
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return "";
    }
}
