package br.com.ferreiradev.fmu.core.domain.repository;

import br.com.ferreiradev.fmu.core.domain.model.FishingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FishingLogRepository extends JpaRepository<FishingLog, Long> {

    @Query("""
           SELECT fl FROM FishingLog fl
           JOIN FETCH fl.fish
           WHERE fl.id = :id
           """)
    Optional<FishingLog> findByIdWithFish(Long id);
}
