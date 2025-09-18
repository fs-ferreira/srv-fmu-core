package br.com.ferreiradev.fmu.core.domain.model;

import br.com.ferreiradev.fmu.core.domain.model.enums.FishingPlace;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_fish_record")
public class FishRecord {
    private Fish fish;
    private Double weight;
    private LocalDate date;
    private FishingPlace place;
}
