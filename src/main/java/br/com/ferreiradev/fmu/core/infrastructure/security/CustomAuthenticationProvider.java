package br.com.ferreiradev.fmu.core.infrastructure.security;

import br.com.ferreiradev.fmu.core.application.mapper.UserMapper;
import br.com.ferreiradev.fmu.core.domain.model.User;
import br.com.ferreiradev.fmu.core.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.constants.ErrorConstants.INVALID_CREDENTIALS_MESSAGE;


@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username  = authentication.getName();
        String pass = authentication.getCredentials().toString();

        User user = repository.findByUsername(username).orElseThrow(this::invalidCredentialsException);

        validatePassword(pass, user);

        return new CustomAuthentication(user, mapper);
    }

    private void validatePassword(String pass, User user) {
        boolean passwordsMatches = encoder.matches(pass, user.getPassword());
        if(!passwordsMatches){
            throw invalidCredentialsException();
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);

    }

    private UsernameNotFoundException invalidCredentialsException(){
        return new UsernameNotFoundException(INVALID_CREDENTIALS_MESSAGE);
    }
}
