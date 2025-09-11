package br.com.ferreiradev.fmu.core.domain.repository;

import br.com.ferreiradev.fmu.core.domain.model.Fish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FishRepository extends JpaRepository<Fish, Long> {
}
