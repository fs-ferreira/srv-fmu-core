package br.com.ferreiradev.fmu.core.infrastructure.configuration;

import br.com.ferreiradev.fmu.core.application.mapper.UserMapper;
import br.com.ferreiradev.fmu.core.application.service.UserService;
import br.com.ferreiradev.fmu.core.domain.model.auth.User;
import br.com.ferreiradev.fmu.core.domain.repository.UserRepository;
import br.com.ferreiradev.fmu.core.infrastructure.security.CustomAuthentication;
import br.com.ferreiradev.fmu.core.presentation.dto.UserRecord;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class JpaConfig implements AuditorAware<User> {

    private final UserRepository repository;

    @Override
    @NonNull
    public Optional<User> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }

        if(!(auth instanceof CustomAuthentication customAuth)){
            return Optional.empty();
        }

        return repository.findByUsername(customAuth.getUser().username());
    }
}
