package br.com.ferreiradev.fmu.core.domain.model;

import br.com.ferreiradev.fmu.core.domain.model.enums.FishingPlace;
import br.com.ferreiradev.fmu.core.infrastructure.id.annotation.SnowflakeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_fish_record")
public class FishingLog {
    @Id
    @SnowflakeId
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fish_id", nullable = false)
    private Fish fish;

    @Column(precision = 5, scale = 2)
    private BigDecimal weight;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private FishingPlace place;

    @Column(nullable = false)
    private Boolean validated = false;
}
