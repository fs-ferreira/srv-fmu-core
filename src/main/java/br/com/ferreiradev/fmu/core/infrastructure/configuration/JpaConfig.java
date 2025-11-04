package br.com.ferreiradev.fmu.core.infrastructure.configuration;

import br.com.ferreiradev.fmu.core.domain.model.User;
import br.com.ferreiradev.fmu.core.infrastructure.security.CustomAuthentication;
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

        return Optional.of(customAuth.getUser());
    }
}
