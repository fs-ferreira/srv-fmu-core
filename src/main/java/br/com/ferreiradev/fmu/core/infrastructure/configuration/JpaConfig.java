package br.com.ferreiradev.fmu.core.infrastructure.configuration;

import br.com.ferreiradev.fmu.core.domain.model.User;
import br.com.ferreiradev.fmu.core.domain.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class JpaConfig implements AuditorAware<User> {

    private final UserRepository userRepository;

    @Override
    @NonNull
    public Optional<User> getCurrentAuditor() {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }

        String username = auth.getName();

        return userRepository.findByUsername(username);
    }
}
