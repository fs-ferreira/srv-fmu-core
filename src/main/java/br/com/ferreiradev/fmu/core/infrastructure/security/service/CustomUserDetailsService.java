package br.com.ferreiradev.fmu.core.infrastructure.security.service;

import br.com.ferreiradev.fmu.core.application.service.UserService;
import br.com.ferreiradev.fmu.core.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.constants.ErrorConstants.INVALID_CREDENTIALS_MESSAGE;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var entity = repository.findByUsername(username).orElseThrow(this::throwException);
        var authorities = entity.getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return User.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .authorities(authorities)
                .build();
    }

    private UsernameNotFoundException throwException(){
        return new UsernameNotFoundException(INVALID_CREDENTIALS_MESSAGE);
    }
}
