package br.com.ferreiradev.fmu.core.domain.repository;

import br.com.ferreiradev.fmu.core.domain.model.FishingLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FishingLogRepository extends JpaRepository<FishingLog, Long> {
}
