package br.com.ferreiradev.fmu.core.domain.repository;

import br.com.ferreiradev.fmu.core.domain.model.auth.AuthClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthClientRepository extends JpaRepository<AuthClient, Long> {

    Optional<AuthClient> findByClientId(String clientId);
}
